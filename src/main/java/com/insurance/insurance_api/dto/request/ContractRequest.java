package com.insurance.insurance_api.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractRequest {
    private long id;
    private LocalDate startDate;
    private LocalDate updateDate;
    private LocalDate endDate;
    private Double costAmount;
}
