package com.oxxo.gtim.controller;

import com.oxxo.gtim.configuration.AppConfigurationProperties;
import com.oxxo.gtim.dto.request.AuthRequest;
import com.oxxo.gtim.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppConfigurationProperties properties;
    private final AuthService authService;

    @PostMapping("/getUser")
    public ResponseEntity<Object> authenticate(@RequestBody AuthRequest request, HttpServletResponse response) {
        try {
            var auth = authService.authenticate(request);

            // Create secure cookie
            ResponseCookie cookie = ResponseCookie.from(properties.getCookie().getName(), auth.getToken())
                    .httpOnly(true)
                    .secure(true)
                    .path("/index")
                    .maxAge(properties.getCookie().getExpiresIn())
                    .sameSite(SameSiteCookies.STRICT.toString())
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(auth);

        } catch (Exception e) {
            log.error("Error authenticating user", e);
            return ResponseEntity.status(401).body("Authentication failed: " + e.getMessage());
        }
    }

}
