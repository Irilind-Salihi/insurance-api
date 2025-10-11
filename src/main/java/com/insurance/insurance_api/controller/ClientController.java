package com.insurance.insurance_api.controller;


import com.insurance.insurance_api.dto.request.ClientPatchRequest;
import com.insurance.insurance_api.dto.request.CompanyRequest;
import com.insurance.insurance_api.dto.request.PersonRequest;
import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Company;
import com.insurance.insurance_api.entity.Person;
import com.insurance.insurance_api.service.ClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{client_id}")
    public Client getClientById(@PathVariable("client_id")  Long clientId){
        return clientService.getClientById(clientId);
    }

    @PostMapping("/person/create")
    public Person createPerson(@RequestBody PersonRequest personRequest){
        return clientService.createPerson(
                personRequest.getName(),
                personRequest.getPhone(),
                personRequest.getEmail(),
                personRequest.getBirthDate()
        );
    }

    @PostMapping("/company/create")
        public Company createCompany(@RequestBody CompanyRequest companyRequest){
        return clientService.createCompany(
                companyRequest.getName(),
                companyRequest.getPhone(),
                companyRequest.getEmail()
        );
    }

    @PatchMapping()
    public void patchClient(@RequestBody ClientPatchRequest clientPatchRequest){
        clientService.patchClientById(clientPatchRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteClientById(@PathVariable Long id){
        clientService.deleteClientById(id);
    }
}
