package com.insurance.insurance_api.controller;

import com.insurance.insurance_api.dto.request.ContractRequest;
import com.insurance.insurance_api.entity.Contract;
import com.insurance.insurance_api.service.ContractService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/create")
    public Contract createContract(@RequestBody ContractRequest contractRequest) {
        return contractService.createContract(contractRequest);
    }
}
