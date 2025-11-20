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
public class Menus {

    private String profileId;
    private String profileName;
    private String profileShortName;
    private String profileDescription;
    private List<Submenus> submenus;
    private String serviceId;
    private String serviceName;
    private String serviceShortName;
    private String serviceDescription;
    private String serviceUrl;
    private String serviceSortOrder;
    private String serviceType;
    private String profileSortOrder;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String serviceAttribute1;
    private String serviceAttribute2;
    private String serviceAttribute3;
    private String serviceAttribute4;
    private String serviceAttribute5;
}
