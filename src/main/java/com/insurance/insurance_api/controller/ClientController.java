package com.insurance.insurance_api.controller;


import com.insurance.insurance_api.dto.request.ClientPatchRequest;
import com.insurance.insurance_api.dto.request.CompanyRequest;
import com.insurance.insurance_api.dto.request.PersonRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Company;
import com.insurance.insurance_api.entity.Person;
import com.insurance.insurance_api.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
@Tag(name = "Clients Controller", description = "Operations about clients, persons, and companies")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{client_id}")
    @Operation(summary = "Get client by ID", description = "Retrieve a client, either a Person or a Company, by its unique id")
    @ApiResponse(responseCode = "200", description = "Client found successfully")
    public ResponseEntity<Client> getClientById(@PathVariable("client_id")  Long clientId){
        Client client = clientService.getClientById(clientId);

        return ResponseEntity.status(HttpStatus.OK).body(client);

    }

    @PostMapping("/person")
    @Operation(summary = "Create a person", description = "Create a new person with name, email, phone, and birth date")
    @ApiResponse(responseCode = "201", description = "Person created successfully")
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
    @Operation(summary = "Create a company", description = "Create a new company with name, email, phone")
    @ApiResponse(responseCode = "201", description = "Company created successfully")
    public ResponseEntity<Company> createCompany(@RequestBody CompanyRequest companyRequest){
       Company company = clientService.createCompany(
                companyRequest.getName(),
                companyRequest.getPhone(),
                companyRequest.getEmail()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(company);

    }

    @PatchMapping()
    @Operation(summary = "Patch a client", description = "Patch a client by changing the name, email, phone")
    @ApiResponse(responseCode = "204", description = "One or multiple field were patch for specific client")
    public ResponseEntity<Void> patchClient(@RequestBody ClientPatchRequest clientPatchRequest){
        clientService.patchClientById(clientPatchRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client", description = "Delete a client by its unique ID")
    @ApiResponse(responseCode = "204", description = "Delete a client for a given id")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id){
        clientService.deleteClientById(id);

        return ResponseEntity.noContent().build();
    }
}
