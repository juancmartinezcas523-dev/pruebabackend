package com.oxxo.gtim.service.impl;

import io.jsonwebtoken.Claims;
import com.oxxo.gtim.configuration.AppConfigurationProperties;
import com.oxxo.gtim.service.CatalogoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.oxxo.gtim.dto.PruebaDTO;
import com.oxxo.gtim.dto.response.InfoTestResponse;
import com.oxxo.gtim.utils.JwtUtils;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {

    private final JwtUtils jwtUtils;
    private final AppConfigurationProperties properties;

    @Override
    public InfoTestResponse getPruebaDatos(String token) {
        InfoTestResponse testData = new InfoTestResponse();
        List<PruebaDTO> datosList = new ArrayList<>();
        PruebaDTO data;

        try {
            var secretKey = jwtUtils.generateSecretKey(properties.getJwt().getSecret());
            Claims cJwt = jwtUtils.getPayload(token, secretKey);

            String userId = cJwt.get("userId", String.class);
            String email = cJwt.get("email", String.class);

            for (int i = 0; i <= 10; i++) {
                data = new PruebaDTO();
                data.setContador(i);
                data.setUsuario(userId);
                data.setCorreo(email);

                datosList.add(data);
            }

                testData.setTitulo("Prueba de obtención de claims");
            testData.setDatos(datosList);
            
        } catch (Exception ex) {
            System.out.println("Error a generar datos pruebas " + ex.getMessage());
        }

        return testData;
    }

}
