package com.insurance.insurance_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContractPatchRequest {
    @JsonProperty("contract_id")
    private Long contractId;

    @JsonProperty("new_amount")
    private BigDecimal newAmount;
}
