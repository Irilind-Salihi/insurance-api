package com.insurance.insurance_api.dto.request;

import lombok.Data;

@Data

public class ClientPatchRequest {
    private Long id;
    private String name;
    private String email;
    private String phone;
}
