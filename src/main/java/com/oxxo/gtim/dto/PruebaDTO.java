package com.oxxo.gtim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PruebaDTO {
    private Integer contador;
    private String usuario;
    private String correo;
}
