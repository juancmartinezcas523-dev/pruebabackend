package com.oxxo.gtim.dto.response;

import com.oxxo.gtim.dto.accessControl.Menus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String userId;
    private String userName;
    private String email;
    private int applicationId;
    private String name;
    private String shortName;
    private String version;
    private String profileId;
    private String profileName;
    private String profileShortName;
    private String token;
}
