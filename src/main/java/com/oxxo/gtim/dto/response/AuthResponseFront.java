package com.oxxo.gtim.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseFront {
    private String userName;
    private String appName;
    private String shortAppName;
    private String version;
    private String profileShortName;
    private String token;
}
