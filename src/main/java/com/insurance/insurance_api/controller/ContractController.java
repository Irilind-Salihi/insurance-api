package com.insurance.insurance_api.controller;

import com.insurance.insurance_api.dto.request.ContractPatchRequest;
import com.insurance.insurance_api.dto.request.ContractRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Contract;
import com.insurance.insurance_api.service.ClientService;
import com.insurance.insurance_api.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contract")
@Tag(name = "Contract Controller", description = "Operations about contract")
public class ContractController {

    private final ContractService contractService;
    private final ClientService clientService;

    public ContractController(ContractService contractService, ClientService clientService) {
        this.contractService = contractService;
        this.clientService = clientService;
    }

    @GetMapping("/active/{client_id}")
    @Operation(summary = "Get all contract from user", description = "Get active contract list for a client with given id")
    @ApiResponse(responseCode = "200", description = "Contract list found successfully")
    public ResponseEntity<List<Contract>> getActiveContractByClientId(@PathVariable("client_id") Long clientId) {
        List<Contract> contracts = contractService.getActiveContractByClientId(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(contracts);

    }

    @GetMapping("/active/{client_id}/filtered")
    @Operation(summary = "Get all contract from user with filter", description = "Get active contract list for a client with given id and filtered by updated date")
    @ApiResponse(responseCode = "200", description = "Contract list found successfully")
    public ResponseEntity<List<Contract>> getFilteredActiveContractByClientIdAndUpdateDate(@PathVariable("client_id") Long clientId, @RequestParam("update_date") LocalDate updateDate) {
        List<Contract> contracts = contractService.getActiveContractByClientIdAndUpdateDate(clientId, updateDate);
        return ResponseEntity.status(HttpStatus.OK).body(contracts);
    }

    @GetMapping("/active/{client_id}/sum")
    @Operation(summary = "Get all active contract from user with filter", description = "Get active contract list for a client with given id and filtered by updated date")
    @ApiResponse(responseCode = "200", description = "Contract list found successfully")
    public ResponseEntity<BigDecimal> sumActiveContractsForClient(@PathVariable("client_id") Long clientId) {
        BigDecimal sum = contractService.sumActiveContractsForClient(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(sum);
    }
    @PostMapping
    @Operation(summary = "Create a contract", description = "Create a contract using client_id, start_date(optional), end_date(optional), cost_amount")
    @ApiResponse(responseCode = "201", description = "Contract list found successfully")
    public ResponseEntity<Contract> createContract(@RequestBody ContractRequest contractRequest) {
        Client client = clientService.getClientById(contractRequest.getId());

        Contract contract = contractService.createContract(client, contractRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(contract);

    }

    @PatchMapping
    @Operation(summary = "Patch a contract", description = "patch a contract by setting client_id and cost_amount")
    @ApiResponse(responseCode = "204", description = "Contract list found successfully")
    public ResponseEntity<Void> patchContract(@RequestBody ContractPatchRequest contractPatchRequest) {
        contractService.updateContract(contractPatchRequest);

        return ResponseEntity.noContent().build();
    }
}
