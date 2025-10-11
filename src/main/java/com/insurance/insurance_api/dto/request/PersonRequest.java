package com.insurance.insurance_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonRequest
{
    @JsonProperty("nqmd")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("birth_date")
    private LocalDate birthDate;
}
