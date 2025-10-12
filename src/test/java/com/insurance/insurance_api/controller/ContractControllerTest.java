package com.insurance.insurance_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.insurance.insurance_api.dto.request.ContractPatchRequest;
import com.insurance.insurance_api.dto.request.ContractRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Contract;
import com.insurance.insurance_api.service.ClientService;
import com.insurance.insurance_api.service.ContractService;
import com.insurance.insurance_api.utils.MockValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ContractControllerTest {

    private ContractService contractService;
    private ClientService clientService;
    private ContractController contractController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        contractService = mock(ContractService.class);
        clientService = mock(ClientService.class);
        contractController = new ContractController(contractService, clientService);

        mockMvc = MockMvcBuilders.standaloneSetup(contractController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    // ---------------- GET /api/contract/active/{client_id} ----------------
    @Test
    void getActiveContractsShouldReturnList() throws Exception {
        List<Contract> contracts = Arrays.asList(MockValue.mockContract1(), MockValue.mockContract2());
        when(contractService.getActiveContractByClientId(1L)).thenReturn(contracts);

        mockMvc.perform(get("/api/contract/active/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(contracts.size()))
                .andExpect(jsonPath("$[0].id").value(contracts.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(contracts.get(1).getId()));

        verify(contractService).getActiveContractByClientId(1L);
    }

    // ---------------- GET /api/contract/active/{client_id}/filtered ----------------
    @Test
    void getFilteredActiveContractsShouldReturnList() throws Exception {
        List<Contract> contracts = Arrays.asList(MockValue.mockContract1());
        LocalDate updateDate = LocalDate.of(2025, 10, 12);

        when(contractService.getActiveContractByClientIdAndUpdateDate(1L, updateDate))
                .thenReturn(contracts);

        mockMvc.perform(get("/api/contract/active/1/filtered")
                        .param("update_date", updateDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(contracts.size()))
                .andExpect(jsonPath("$[0].id").value(contracts.get(0).getId()));

        verify(contractService).getActiveContractByClientIdAndUpdateDate(1L, updateDate);
    }

    // ---------------- GET /api/contract/active/{client_id}/sum ----------------
    @Test
    void sumActiveContractsShouldReturnAmount() throws Exception {
        BigDecimal sum = new BigDecimal("1000.50");
        when(contractService.sumActiveContractsForClient(1L)).thenReturn(sum);

        mockMvc.perform(get("/api/contract/active/1/sum"))
                .andExpect(status().isOk())
                .andExpect(content().string(sum.toString()));

        verify(contractService).sumActiveContractsForClient(1L);
    }

    // ---------------- POST /api/contract ----------------
    @Test
    void createContractShouldReturnCreatedContract() throws Exception {
        Client client = MockValue.mockClient1();
        Contract contract = MockValue.mockContract1();

        ContractRequest request = new ContractRequest();
        request.setId(client.getId());
        request.setStartDate(contract.getStartDate());
        request.setEndDate(contract.getEndDate());
        request.setCostAmount(contract.getCostAmount());

        when(clientService.getClientById(client.getId())).thenReturn(client);
        when(contractService.createContract(client, request)).thenReturn(contract);

        mockMvc.perform(post("/api/contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(contract.getId()))
                .andExpect(jsonPath("$.costAmount").value(contract.getCostAmount()));

        verify(clientService).getClientById(client.getId());
        verify(contractService).createContract(client, request);
    }

    // ---------------- PATCH /api/contract ----------------
    @Test
    void patchContractShouldReturnNoContent() throws Exception {
        ContractPatchRequest request = new ContractPatchRequest();
        request.setContractId(1L);
        request.setNewAmount(new BigDecimal("500"));

        doNothing().when(contractService).updateContract(request);

        mockMvc.perform(patch("/api/contract")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(contractService).updateContract(request);
    }
}