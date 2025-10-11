package com.insurance.insurance_api.dto.request;

import lombok.Data;

@Data
public class ContractPatchRequest {
    private Long contractId;
    private Double newAmount;
}
