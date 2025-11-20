package com.oxxo.gtim.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oxxo.gtim.configuration.AppConfigurationProperties;
import com.oxxo.gtim.dto.accessControl.Menus;
import com.oxxo.gtim.dto.accessControl.UserInformation;
import com.oxxo.gtim.dto.request.AuthRequest;
import com.oxxo.gtim.dto.response.AuthResponse;
import com.oxxo.gtim.service.AuthService;
import com.oxxo.gtim.utils.JwtUtils;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final JwtUtils jwtUtils;
    private final AppConfigurationProperties properties;
    
    CertificateFactory certificateFactory;
    FileInputStream inputStream;
    X509Certificate cert;
    KeyStore keyStore;
    TrustManagerFactory trustManagerFactory;
    SSLContext sslContext;
    
    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier() {
            
            public boolean verify(String hostname,
                    javax.net.ssl.SSLSession sslSession) {
                if (hostname.equals("fcportaldes.femcom.net")) {
                    return true;
                }
                return false;
            }
        });
    }
    
    @Override
    public AuthResponse authenticate(AuthRequest request) {
        AuthResponse response = new AuthResponse();
        try {
            validateAuthRequest(request);
            
            boolean isRequestValid = false;
            
            if (request.getAppId() != null && request.getEncrypt() != null && request.getToken() != null) {
                isRequestValid = true;
            }
            
            if (isRequestValid) {
                response = getUserInformation(request);
                
                var secretKey = jwtUtils.generateSecretKey(properties.getJwt().getSecret());
                
                Map<String, String> claims = new HashMap<>();
                claims.put("userId", response.getUserId());
                claims.put("userName", response.getUserName());
                claims.put("email", response.getEmail());
                claims.put("applicationId", String.valueOf(response.getApplicationId()));
                claims.put("name", response.getName());
                claims.put("shortName", response.getShortName());
                claims.put("version", response.getVersion());
                claims.put("profileId", response.getProfileId());
                claims.put("profileName", response.getProfileName());
                claims.put("profileShortName", response.getShortName());
                
                String audience = UUID.randomUUID().toString();
                
                String token = jwtUtils.generateToken(response.getUserId(), audience, claims, properties.getJwt().getExpiresIn(), secretKey);
                
                response.setToken(token);
                
            } else {
                System.out.println("Parametros Invalidos");
                throw new RuntimeException("PArametros Invalidos");
            }
            
        } catch (Exception ex) {
        }
        
        return response;
    }
    
    private void validateAuthRequest(AuthRequest request) {
        Objects.requireNonNull(request, "appId, encrypt and token cannot be null");
        Objects.requireNonNull(request.getAppId(), "AppId cannot be null");
        Objects.requireNonNull(request.getEncrypt(), "Encrypt cannot be null");
        Objects.requireNonNull(request.getToken(), "Token cannot be null");
    }
    
    private AuthResponse getUserInformation(AuthRequest request) {
        AuthResponse dat = new AuthResponse();
        int status = 0;
        String mensaje = "";
        boolean errorUser = false;
        String urlUsers = "https://fcportaldes.femcom.net:8443/AuthFemcoProxBack/api/user";
        StringBuilder users = new StringBuilder();
        users.append(urlUsers).append("?appId=").append(request.getAppId()).append("&encrypt=").append(request.getEncrypt()).append("&token=").append(request.getToken());
        URL url;
        HttpsURLConnection con = null;
        StringBuffer content = null;
        Gson gson = new Gson();
        UserInformation ui = null;
        try {
            agregarCertificado();
            Type usersList = new TypeToken<UserInformation>() {
            }.getType();
            url = new URL(users.toString());
            con = (HttpsURLConnection) url.openConnection();
            con.setSSLSocketFactory(sslContext.getSocketFactory());
            status = con.getResponseCode();
            System.out.println("status users: " + status);
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                ui = new UserInformation();
                ui = gson.fromJson(content.toString(), usersList);
            } else {
                errorUser = true;
            }
            
        } catch (Exception ex) {
            System.out.println("No fue posible obtener la configuración del usuario");
            errorUser = true;
        }
        
        if (!errorUser) {
            
            String perfilId = null;
            String perfilName = null;
            String perfilShortName = null;
            
            for (Menus m : ui.getMenus()) {
                perfilId = m.getProfileId();
                perfilName = m.getProfileName();
                perfilShortName = m.getProfileShortName();
            }
            
            dat.setUserId(ui.getUser().getUserId());
            dat.setUserName(ui.getUser().getUserName());
            dat.setEmail(ui.getUser().getEmail());
            dat.setApplicationId(ui.getApplication().getApplicationId());
            dat.setName(ui.getApplication().getName());
            dat.setShortName(ui.getApplication().getShortName());
            dat.setVersion(ui.getApplication().getVersion());
            dat.setProfileId(perfilId);
            dat.setProfileName(perfilName);
            dat.setProfileShortName(perfilShortName);
        } else {
            System.out.println("No fue posible obtener la configuración del usuario");
        }
        
        return dat;
    }
    
    private void agregarCertificado() {
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            InputStream resource = new ClassPathResource("fcportaldes.femcom.net.crt").getInputStream();
            cert = (X509Certificate) certificateFactory.generateCertificate(resource);

            // Configure SSL context            
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("fcportaldes.femcom.net", cert);
            
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        } catch (Exception ex) {
            System.out.println("Error al agregar certificado: " + ex.getMessage() + " " + ex.getCause());
        }
    }
    
}
