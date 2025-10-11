package com.insurance.insurance_api.service;

import com.insurance.insurance_api.dto.request.ContractRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Contract;
import com.insurance.insurance_api.repository.ContractRepository;
import com.insurance.insurance_api.utils.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class ContractService {

    private final ClientService clientService;
    private final ContractRepository contractRepository;

    public ContractService(ClientService clientService, ContractRepository contractRepository) {
        this.clientService = clientService;
        this.contractRepository = contractRepository;
    }

    public Contract createContract(ContractRequest contractRequest) {
        Client client = clientService.getClientById(contractRequest.getId());

        Contract contract= new Contract();
        contract.setClient(client);

        LocalDate startDate = contractRequest.getStartDate() != null
                ? contractRequest.getStartDate()
                : LocalDate.now();

        Validator.isValidISODate(startDate.toString());
        contract.setStartDate(startDate);

        contract.setUpdateDate(startDate);

        LocalDate endDate = contractRequest.getEndDate();
        if (endDate != null) {
            Validator.isValidEndDate(startDate, endDate);
            contract.setEndDate(endDate);

        }

        Validator.isValidAmount(contractRequest.getCostAmount());
        contract.setCostAmount(contractRequest.getCostAmount());

        return contractRepository.save(contract);
    }

}
