package com.insurance.insurance_api.service;

import com.insurance.insurance_api.dto.request.ContractPatchRequest;
import com.insurance.insurance_api.dto.request.ContractRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Contract;
import com.insurance.insurance_api.repository.ContractRepository;
import com.insurance.insurance_api.utils.Validator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Contract getContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract with id " + contractId + " not found"));
    }

    public Contract createContract(Client client, ContractRequest contractRequest) {

        Contract contract= new Contract();

        LocalDate startDate = contractRequest.getStartDate() != null
                ? contractRequest.getStartDate()
                : LocalDate.now();

        Validator.isValidAmount(contractRequest.getCostAmount());
        Validator.isValidISODate(startDate.toString());


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
        Validator.isValidISODate(updatedDate.toString());
        contract.setUpdateDate(updatedDate);

        contractRepository.save(contract);
    }


    public void endAllContractsForClient(Client client) {

        LocalDate todayDate = LocalDate.now();

        List<Contract> contracts = client.getContracts();
        for (Contract contract : contracts) {
            contract.setEndDate(todayDate);
            contractRepository.save(contract);
        }
    }

}
