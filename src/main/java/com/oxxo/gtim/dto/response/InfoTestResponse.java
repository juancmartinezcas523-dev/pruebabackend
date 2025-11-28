package com.oxxo.gtim.dto.response;

import com.oxxo.gtim.dto.PruebaDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoTestResponse {
    private String titulo;
    private List<PruebaDTO> datos;
}
