package com.insurance.insurance_api.controller;

import com.insurance.insurance_api.dto.request.ContractPatchRequest;
import com.insurance.insurance_api.dto.request.ContractRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Contract;
import com.insurance.insurance_api.service.ClientService;
import com.insurance.insurance_api.service.ContractService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;
    private final ClientService clientService;

    public ContractController(ContractService contractService, ClientService clientService) {
        this.contractService = contractService;
        this.clientService = clientService;
    }

    @PostMapping
    public Contract createContract(@RequestBody ContractRequest contractRequest) {

        Client client = clientService.getClientById(contractRequest.getId());
        return contractService.createContract(client, contractRequest);
    }

    @PatchMapping
    public void patchContract(@RequestBody ContractPatchRequest contractPatchRequest) {
        contractService.updateContract(contractPatchRequest);
    }
}
