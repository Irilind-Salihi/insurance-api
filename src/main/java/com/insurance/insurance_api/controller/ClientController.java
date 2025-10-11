package com.insurance.insurance_api.controller;


import com.insurance.insurance_api.dto.request.CompanyRequest;
import com.insurance.insurance_api.dto.request.PersonRequest;
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


    @PostMapping("/person/create")
    public Person createPerson(@RequestBody PersonRequest request){
        return clientService.createPerson(
                request.getName(),
                request.getPhone(),
                request.getEmail(),
                request.getBirthDate()
        );
    }

    @PostMapping("/company/create")
        public Company createCompany(@RequestBody CompanyRequest request){
        return clientService.createCompany(
                request.getName(),
                request.getPhone(),
                request.getEmail(),
                request.getCompanyIdentifier()
        );
    }
}
