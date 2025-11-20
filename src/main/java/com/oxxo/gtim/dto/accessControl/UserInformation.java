package com.oxxo.gtim.dto.accessControl;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {

    private User user;
    private Application application;
    private List<Menus> menus;
}
