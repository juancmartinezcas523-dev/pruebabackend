package com.oxxo.gtim.dto.accessControl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    private int applicationId;
    private String name;
    private String version;
    private String shortName;
    private String description;
    private String validFromDate;
    private String validToDate;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String image;
}
