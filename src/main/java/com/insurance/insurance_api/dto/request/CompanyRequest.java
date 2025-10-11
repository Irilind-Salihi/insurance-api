package com.insurance.insurance_api.dto.request;

import lombok.Data;

@Data
public class CompanyRequest {
    private String name;
    private String email;
    private String phone;
    private String companyIdentifier;
}

