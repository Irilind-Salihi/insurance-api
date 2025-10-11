package com.insurance.insurance_api.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonRequest
{
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;
}
