package com.oxxo.gtim.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.oxxo.gtim.dto.response.InfoTestResponse;
import com.oxxo.gtim.service.CatalogoService;
import jakarta.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    CatalogoService catService;

    @GetMapping("/getDatos")
    public ResponseEntity<Map<String, Object>> getDatos(HttpServletRequest request) throws ParseException {
        final String authorizationHeaderValue = request.getHeader("Authorization");
        InfoTestResponse itr = new InfoTestResponse();

        if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("OXXO")) {
            String token = authorizationHeaderValue.replace("OXXO", "").trim();
            Map<String, Object> response = new HashMap<>();
            itr = catService.getPruebaDatos(token);
            response.put("datos", itr);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
