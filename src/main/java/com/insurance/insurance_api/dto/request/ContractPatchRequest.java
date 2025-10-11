package com.insurance.insurance_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContractPatchRequest {
    @JsonProperty("contract_id")
    private Long contractId;

    @JsonProperty("new_amount")
    private Double newAmount;
}
