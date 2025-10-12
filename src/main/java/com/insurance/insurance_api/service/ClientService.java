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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

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

    @Transactional
    public Person createPerson(String name, String phone, String email, LocalDate birthdate) {
        checkUniqueEmail(email);
        checkUniquePhone(phone);
        Validator.isValidName(name);
        Validator.isValidEmail(email);
        Validator.isValidPhoneNumber(phone);
        Validator.isValidISODate(birthdate);

        Person person = new Person();
        person.setName(name);
        person.setEmail(email);
        person.setPhone(phone);
        person.setBirthdate(birthdate);

        return personRepository.save(person);
    }

    @Transactional
    public Company createCompany(String name, String phone, String email ) {
        checkUniqueEmail(email);
        checkUniquePhone(phone);
        Validator.isValidEmail(email);
        Validator.isValidPhoneNumber(phone);

        String identifier = Utils.generateCompanyIdentifier(name, companyRepository);

        Company company = new Company();
        Validator.isValidName(name);
        company.setName(name);
        company.setEmail(email);
        company.setPhone(phone);
        company.setCompanyIdentifier(identifier);

        return companyRepository.save(company);

    }

    @Transactional
    public void patchClientById(ClientPatchRequest  clientPatchRequest) {
        if (clientPatchRequest.getName() == null && clientPatchRequest.getEmail() == null && clientPatchRequest.getPhone() == null) {
            throw new IllegalArgumentException("At least one field must be provided in addtion to the client id to patch");
        }


        Client client = getClientById(clientPatchRequest.getId());

        if (clientPatchRequest.getName() != null) {
            Validator.isValidName(clientPatchRequest.getName());
            client.setName(clientPatchRequest.getName());
        }

        if (clientPatchRequest.getEmail() != null) {
            if (Objects.equals(clientPatchRequest.getEmail(), client.getEmail())) {
                throw new IllegalArgumentException("This is already you're current mail");
            }
            checkUniqueEmail(clientPatchRequest.getEmail());
            Validator.isValidEmail(clientPatchRequest.getEmail());
            client.setEmail(clientPatchRequest.getEmail());
        }
        if (clientPatchRequest.getPhone() != null) {
            if (Objects.equals(clientPatchRequest.getPhone(), client.getPhone())) {
                throw new IllegalArgumentException("This is already you're phone number");
            }
            checkUniquePhone(clientPatchRequest.getPhone());
            Validator.isValidPhoneNumber(clientPatchRequest.getPhone());
            client.setPhone(clientPatchRequest.getPhone());
        }

        clientRepository.save(client);
    }

    @Transactional
    public void deleteClientById(Long clientId) {
        Client client = getClientById(clientId);

        contractService.endAllContractsForClient(clientId);

        clientRepository.delete(client);
    }


    private void checkUniqueEmail(String email) {
        if (email != null && clientRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already used: " + email);
        }
    }


    private void checkUniquePhone(String phone) {
        if (phone != null && clientRepository.existsByPhone(phone)) {
            throw new IllegalArgumentException("Phone number already used: " + phone);
        }
    }
}
