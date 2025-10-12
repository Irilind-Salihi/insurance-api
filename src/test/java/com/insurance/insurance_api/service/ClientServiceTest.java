package com.insurance.insurance_api.service;

import com.insurance.insurance_api.dto.request.ClientPatchRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Company;
import com.insurance.insurance_api.entity.Person;
import com.insurance.insurance_api.repository.ClientRepository;
import com.insurance.insurance_api.repository.CompanyRepository;
import com.insurance.insurance_api.repository.PersonRepository;
import com.insurance.insurance_api.utils.MockValue;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    private PersonRepository personRepository;
    private CompanyRepository companyRepository;
    private ClientRepository clientRepository;
    private ContractService contractService;
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepository.class);
        companyRepository = mock(CompanyRepository.class);
        clientRepository = mock(ClientRepository.class);
        contractService = mock(ContractService.class);
        clientService = new ClientService(personRepository, companyRepository, clientRepository, contractService);
    }

    // ---------------Get Client By Id Test-----------------------------+
    @Test
    void getClientByIdShouldReturnClientIfExists() {
        Client client = MockValue.mockClient1();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client found = clientService.getClientById(1L);

        assertEquals(client, found);
        verify(clientRepository).findById(1L);
    }
    
    @Test
     void getClientByIdShouldThrowExceptionIfNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.getClientById(1L));
    }

    // ---------------Create Person Test-----------------------------+
    @Test
    void createPersonShouldValidateAndSavePerson() {
        Person mockPerson = MockValue.mockPerson1();
        when(personRepository.save(any(Person.class))).thenReturn(mockPerson);

        Person result = clientService.createPerson(
                mockPerson.getName(),
                mockPerson.getPhone(),
                mockPerson.getEmail(),
                mockPerson.getBirthdate()
        );

        assertEquals(mockPerson, result);
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void createPersonShouldThrowExceptionIfEmailAlreadyExists() {
        Person mockPerson = MockValue.mockPerson1();
        when(clientRepository.existsByEmail(mockPerson.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                clientService.createPerson(
                        mockPerson.getName(),
                        mockPerson.getPhone(),
                        mockPerson.getEmail(),
                        mockPerson.getBirthdate()
                ));
        verify(personRepository, never()).save(any(Person.class));
    }

    @Test
    void createPersonShouldThrowExceptionIfPhoneAlreadyExists() {
        Person mockPerson = MockValue.mockPerson1();
        when(clientRepository.existsByEmail(mockPerson.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                clientService.createPerson(
                        mockPerson.getName(),
                        mockPerson.getPhone(),
                        mockPerson.getEmail(),
                        mockPerson.getBirthdate()
                ));
        verify(personRepository, never()).save(any(Person.class));
    }

    // ---------------Create Company By Id Test-----------------------------+
    @Test
    void createCompanyShouldValidateAndSaveCompany() {
        Company mockCompany = MockValue.mockCompany1();
        when(companyRepository.save(any(Company.class))).thenReturn(mockCompany);

        Company result = clientService.createCompany(
                mockCompany.getName(),
                mockCompany.getPhone(),
                mockCompany.getEmail()
        );
        result.setCompanyIdentifier(mockCompany.getCompanyIdentifier());

        assertEquals(mockCompany, result);
        verify(companyRepository).save(any(Company.class));
    }

    @Test
    void createCompanyShouldThrowExceptionIfEmailAlreadyExists() {
        Company mockCompany = MockValue.mockCompany1();
        when(clientRepository.existsByEmail(mockCompany.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                clientService.createCompany(
                        mockCompany.getName(),
                        mockCompany.getPhone(),
                        mockCompany.getEmail()
                ));

        verify(companyRepository, never()).save(any(Company.class));
    }

    @Test
    void createCompanyShouldThrowExceptionIfPhoneAlreadyExists() {
        Company mockCompany = MockValue.mockCompany1();
        when(clientRepository.existsByPhone(mockCompany.getPhone())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                clientService.createCompany(
                        mockCompany.getName(),
                        mockCompany.getPhone(),
                        mockCompany.getEmail()
                ));

        verify(companyRepository, never()).save(any(Company.class));
    }

    // ---------------Updating Client Test-----------------------------+
    @Test
    void patchClientByIdShouldUpdateFields() {
        Client client = MockValue.mockClient1();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        ClientPatchRequest request = new ClientPatchRequest();
        request.setId(1L);
        request.setName("Updated Client");
        request.setEmail("updated@example.com");

        clientService.patchClientById(request);

        assertEquals("Updated Client", client.getName());
        assertEquals("updated@example.com", client.getEmail());
        verify(clientRepository).save(client);
    }

    @Test
    void patchClientShouldThrowExceptionIfEmailAlreadyExists() {
        Client mockClient = MockValue.mockClient1();


        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockClient));
        when(clientRepository.existsByEmail(mockClient.getEmail())).thenReturn(true);

        ClientPatchRequest request = new ClientPatchRequest();
        request.setId(mockClient.getId());
        request.setEmail(mockClient.getEmail());

        assertThrows(IllegalArgumentException.class, () -> clientService.patchClientById(request));
    }

    @Test
    void patchClientShouldThrowExceptionIfPhoneAlreadyExists() {
        Client mockClient = MockValue.mockClient1();


        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockClient));
        when(clientRepository.existsByEmail(mockClient.getEmail())).thenReturn(true);

        ClientPatchRequest request = new ClientPatchRequest();
        request.setId(mockClient.getId());
        request.setEmail(mockClient.getEmail());

        assertThrows(IllegalArgumentException.class, () -> clientService.patchClientById(request));
    }

    @Test
    void patchClientByIdShouldThrowExceptionIfNoFieldsProvided() {
        ClientPatchRequest request = new ClientPatchRequest();
        request.setId(1L);

        assertThrows(IllegalArgumentException.class, () -> clientService.patchClientById(request));
    }

    @Test
    void patchClientByIdShouldThrowExceptionIfClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        ClientPatchRequest request = new ClientPatchRequest();
        request.setId(1L);
        request.setName("New Name");

        assertThrows(EntityNotFoundException.class, () -> clientService.patchClientById(request));
    }

    // ---------------Deleting Client Test-----------------------------+
    @Test
    void deleteClientByIdShouldCallContractServiceAndDelete() {
        Client client = MockValue.mockClient1();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        clientService.deleteClientById(1L);

        verify(contractService).endAllContractsForClient(client.getId());
        verify(clientRepository).delete(client);
    }

    @Test
    void deleteClientByIdShouldThrowExceptionIfClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.deleteClientById(1L));
    }

}
