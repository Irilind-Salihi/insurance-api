package com.insurance.insurance_api.service;

import com.insurance.insurance_api.dto.request.ClientPatchRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Company;
import com.insurance.insurance_api.entity.Person;
import com.insurance.insurance_api.repository.ClientRepository;
import com.insurance.insurance_api.repository.CompanyRepository;
import com.insurance.insurance_api.repository.PersonRepository;
import com.insurance.insurance_api.utils.Utils;
import com.insurance.insurance_api.utils.Validator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClientService {

    private final PersonRepository personRepository;
    private final CompanyRepository companyRepository;
    private final ClientRepository clientRepository;
    private final ContractService contractService;


    public ClientService(PersonRepository personRepository, CompanyRepository companyRepository, ClientRepository clientRepository, ContractService contractService) {
        this.personRepository = personRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
        this.contractService = contractService;
    }

    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId + " not found"));
    }


    public Person createPerson(String name, String phone, String email, LocalDate birthdate) {
        Person person = new Person();

        Validator.isValidName(name);
        Validator.isValidEmail(email);
        Validator.isValidPhoneNumber(phone);
        Validator.isValidISODate(birthdate);


        person.setName(name);
        person.setEmail(email);
        person.setPhone(phone);
        person.setBirthdate(birthdate);

        return personRepository.save(person);
    }

    public Company createCompany(String name, String phone, String email ) {
        Company company = new Company();

        String identifier = Utils.generateCompanyIdentifier(name, companyRepository);

        Validator.isValidEmail(email);
        Validator.isValidPhoneNumber(phone);

        company.setName(name);
        company.setEmail(email);
        company.setPhone(phone);
        company.setCompanyIdentifier(identifier);



        return companyRepository.save(company);

    }

    public void patchClientById(ClientPatchRequest  clientPatchRequest) {
        if (clientPatchRequest.getName() == null && clientPatchRequest.getEmail() == null && clientPatchRequest.getPhone() == null) {
            throw new IllegalArgumentException("At least one field must be provided in addtion to the client id to patch");
        }
        Client client = getClientById(clientPatchRequest.getId());

        if (clientPatchRequest.getName() != null) {
            client.setName(clientPatchRequest.getName());
        }

        if (clientPatchRequest.getEmail() != null) {
            Validator.isValidEmail(clientPatchRequest.getEmail());
            client.setEmail(clientPatchRequest.getEmail());
        }
        if (clientPatchRequest.getPhone() != null) {
            Validator.isValidPhoneNumber(clientPatchRequest.getPhone());
            client.setPhone(clientPatchRequest.getPhone());
        }

        clientRepository.save(client);
    }

    public void deleteClientById(Long clientId) {
        Client client = getClientById(clientId);

        contractService.endAllContractsForClient(client);

        clientRepository.delete(client);
    }

}
