package com.oxxo.gtim.dto.accessControl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String userId;
    private String userName;
    private String userpass;
    private String creationDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;
    private String validFromDate;
    private String validToDate;
    private String passwordExpiration;
    private String email;
    private String employeeId;
    private String userType;
    private String reset;
    private String name;
    private String lastNameOne;
    private String lastNameTwo;
    private String authoUserId;
    private String[] loginDate;
}
