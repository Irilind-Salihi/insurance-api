package com.insurance.insurance_api.service;

import com.insurance.insurance_api.dto.request.ContractPatchRequest;
import com.insurance.insurance_api.dto.request.ContractRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Contract;
import com.insurance.insurance_api.repository.ClientRepository;
import com.insurance.insurance_api.repository.ContractRepository;
import com.insurance.insurance_api.utils.MockValue;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContractServiceTest {
    private ContractRepository contractRepository;
    private ClientRepository clientRepository;
    private ContractService contractService;

    @BeforeEach
    void setUp() {
        contractRepository = mock(ContractRepository.class);
        clientRepository = mock(ClientRepository.class);
        contractService = new ContractService(contractRepository, clientRepository);
    }

    // ---------------- getContractById ----------------
    @Test
    void getContractByIdShouldReturnContract() {
        Contract mockContract = MockValue.mockContract1();
        when(contractRepository.findById(1L)).thenReturn(Optional.of(mockContract));

        Contract result = contractService.getContractById(1L);

        assertEquals(mockContract, result);
        verify(contractRepository).findById(1L);
    }

    @Test
    void getContractByIdShouldThrowExceptionWhenNotFound() {
        when(contractRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> contractService.getContractById(1L));
        verify(contractRepository).findById(1L);
    }

    // ---------------- getActiveContractByClientId ----------------
    @Test
    void getActiveContractByClientIdShouldReturnContracts() {
        Client client = MockValue.mockClient1();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        List<Contract> contracts = Arrays.asList(MockValue.mockContract1(), MockValue.mockContract2());
        when(contractRepository.findActiveContractsByClientId(1L)).thenReturn(contracts);

        List<Contract> result = contractService.getActiveContractByClientId(1L);

        assertEquals(2, result.size());
        verify(clientRepository).findById(1L);
        verify(contractRepository).findActiveContractsByClientId(1L);
    }

    @Test
    void getActiveContractByClientIdShouldThrowWhenClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> contractService.getActiveContractByClientId(1L));
        verify(clientRepository).findById(1L);
    }

    // ---------------- sumActiveContractsForClient ----------------
    @Test
    void sumActiveContractsForClientShouldReturnSum() {
        Client client = MockValue.mockClient1();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(contractRepository.sumActiveContractsForClient(1L)).thenReturn(new BigDecimal("1500"));

        BigDecimal result = contractService.sumActiveContractsForClient(1L);

        assertEquals(new BigDecimal("1500"), result);
        verify(clientRepository).findById(1L);
        verify(contractRepository).sumActiveContractsForClient(1L);
    }

    // ---------------- createContract ----------------
    @Test
    void createContractShouldReturnContract() {
        Client client = MockValue.mockClient1();
        ContractRequest request = new ContractRequest();
        request.setCostAmount(new BigDecimal("500"));
        request.setStartDate(LocalDate.of(2025, 10, 12));
        request.setEndDate(LocalDate.of(2025, 12, 31));

        Contract savedContract = MockValue.mockContract1();
        when(contractRepository.save(any(Contract.class))).thenReturn(savedContract);

        Contract result = contractService.createContract(client, request);

        assertNotNull(result);
        assertEquals(savedContract, result);
        verify(contractRepository).save(any(Contract.class));
    }

    // ---------------- updateContract ----------------
    @Test
    void updateContractShouldUpdateContract() {
        Contract contract = MockValue.mockContract1();
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));

        ContractPatchRequest request = new ContractPatchRequest();
        request.setContractId(1L);
        request.setNewAmount(new BigDecimal("999"));

        contractService.updateContract(request);

        assertEquals(new BigDecimal("999"), contract.getCostAmount());
        verify(contractRepository).save(contract);
    }

    @Test
    void updateContractShouldThrowWhenNotFound() {
        when(contractRepository.findById(1L)).thenReturn(Optional.empty());

        ContractPatchRequest request = new ContractPatchRequest();
        request.setContractId(1L);
        request.setNewAmount(new BigDecimal("999"));

        assertThrows(EntityNotFoundException.class, () -> contractService.updateContract(request));
    }
}