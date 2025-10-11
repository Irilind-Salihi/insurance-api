package com.insurance.insurance_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContractRequest {
    @JsonProperty("client_id")
    private long id;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("cost_amount")
    private BigDecimal costAmount;
}
