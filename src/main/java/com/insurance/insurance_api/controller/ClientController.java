package com.insurance.insurance_api.controller;


import com.insurance.insurance_api.dto.request.ClientPatchRequest;
import com.insurance.insurance_api.dto.request.CompanyRequest;
import com.insurance.insurance_api.dto.request.PersonRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Company;
import com.insurance.insurance_api.entity.Person;
import com.insurance.insurance_api.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{client_id}")
    public ResponseEntity<Client> getClientById(@PathVariable("client_id")  Long clientId){
        Client client = clientService.getClientById(clientId);

        return ResponseEntity.status(HttpStatus.OK).body(client);

    }

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody PersonRequest personRequest){
        Person person = clientService.createPerson(
                personRequest.getName(),
                personRequest.getPhone(),
                personRequest.getEmail(),
                personRequest.getBirthDate()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    @PostMapping("/company")
    public ResponseEntity<Company> createCompany(@RequestBody CompanyRequest companyRequest){
       Company company = clientService.createCompany(
                companyRequest.getName(),
                companyRequest.getPhone(),
                companyRequest.getEmail()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(company);

    }

    @PatchMapping()
    public ResponseEntity<Void> patchClient(@RequestBody ClientPatchRequest clientPatchRequest){
        clientService.patchClientById(clientPatchRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id){
        clientService.deleteClientById(id);

        return ResponseEntity.noContent().build();
    }
}
