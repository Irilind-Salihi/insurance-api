package com.insurance.insurance_api.service;

import com.insurance.insurance_api.dto.request.ContractPatchRequest;
import com.insurance.insurance_api.dto.request.ContractRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Contract;
import com.insurance.insurance_api.repository.ClientRepository;
import com.insurance.insurance_api.repository.ContractRepository;
import com.insurance.insurance_api.utils.Validator;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;

    public ContractService(ContractRepository contractRepository, ClientRepository clientRepository) {
        this.contractRepository = contractRepository;
        this.clientRepository = clientRepository;
    }

    public Contract getContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract with id " + contractId + " not found"));
    }

    public List<Contract> getActiveContractByClientId(Long clientId) {
        clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId + " not found"));

        return contractRepository.findActiveContractsByClientId(clientId);
    }

    public List<Contract> getActiveContractByClientIdAndUpdateDate(Long clientId, LocalDate updateDate) {
        clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId + " not found"));

        Validator.isValidISODate(updateDate);
        return contractRepository.findActiveContractsByClientIdAndUpdateDate(clientId, updateDate);
    }

    public BigDecimal sumActiveContractsForClient(Long clientId) {
        clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId + " not found"));

        return contractRepository.sumActiveContractsForClient(clientId);

    }

    public Contract createContract(Client client, ContractRequest contractRequest) {

        Contract contract= new Contract();

        LocalDate startDate = contractRequest.getStartDate() != null
                ? contractRequest.getStartDate()
                : LocalDate.now();

        Validator.isValidAmount(contractRequest.getCostAmount());
        Validator.isValidISODate(startDate);


        contract.setStartDate(startDate);
        contract.setUpdateDate(startDate);
        contract.setClient(client);
        contract.setCostAmount(contractRequest.getCostAmount());

        LocalDate endDate = contractRequest.getEndDate();
        if (endDate != null) {
            Validator.isValidEndDate(startDate, endDate);
            contract.setEndDate(endDate);

        }

        return contractRepository.save(contract);
    }

    public void updateContract(ContractPatchRequest contractPatchRequest) {
        Contract contract = getContractById(contractPatchRequest.getContractId());

        Validator.isValidAmount(contractPatchRequest.getNewAmount());
        contract.setCostAmount(contractPatchRequest.getNewAmount());

        LocalDate updatedDate = LocalDate.now();
        Validator.isValidISODate(updatedDate);
        contract.setUpdateDate(updatedDate);

        contractRepository.save(contract);
    }


    public void endAllContractsForClient(Long clientId) {
        contractRepository.endAllContractsForClient(clientId);
    }



}
