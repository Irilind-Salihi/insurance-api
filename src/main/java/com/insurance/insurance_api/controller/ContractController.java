package com.insurance.insurance_api.controller;

import com.insurance.insurance_api.dto.request.ContractPatchRequest;
import com.insurance.insurance_api.dto.request.ContractRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Contract;
import com.insurance.insurance_api.service.ClientService;
import com.insurance.insurance_api.service.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    public ResponseEntity<Contract> createContract(@RequestBody ContractRequest contractRequest) {

        Client client = clientService.getClientById(contractRequest.getId());
        Contract contract = contractService.createContract(client, contractRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(contract);

    }

    @PatchMapping
    public ResponseEntity<Void> patchContract(@RequestBody ContractPatchRequest contractPatchRequest) {
        contractService.updateContract(contractPatchRequest);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active/{client_id}")
    public ResponseEntity<List<Contract>> getActiveContractByClientId(@PathVariable("client_id") Long clientId) {
        List<Contract> contracts = contractService.getActiveContractByClientId(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(contracts);

    }

    @GetMapping("/active/{client_id}/filtered")
    public ResponseEntity<List<Contract>> getFilteredActiveContractByClientIdAndUpdateDate(@PathVariable("client_id") Long clientId, @RequestParam("update_date") LocalDate updateDate) {
        List<Contract> contracts = contractService.getActiveContractByClientIdAndUpdateDate(clientId, updateDate);
        return ResponseEntity.status(HttpStatus.OK).body(contracts);
    }

    @GetMapping("/active/{client_id}/sum")
    public ResponseEntity<BigDecimal> sumActiveContractsForClient(@PathVariable("client_id") Long clientId) {
        BigDecimal sum = contractService.sumActiveContractsForClient(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(sum);
    }
}
