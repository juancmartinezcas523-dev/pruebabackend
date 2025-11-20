package com.oxxo.gtim.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String appId;
    private String encrypt;
    private String token;
}
