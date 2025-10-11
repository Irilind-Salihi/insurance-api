package com.insurance.insurance_api.service;

import com.insurance.insurance_api.entity.Company;
import com.insurance.insurance_api.entity.Person;
import com.insurance.insurance_api.repository.CompanyRepository;
import com.insurance.insurance_api.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClientService {

    private final PersonRepository personRepository;
    private final CompanyRepository companyRepository;

    public ClientService(PersonRepository personRepository, CompanyRepository companyRepository) {
        this.personRepository = personRepository;
        this.companyRepository = companyRepository;
    }

    public Person createPerson(String name, String phone, String email, LocalDate birthdate) {
        Person person = new Person();
        person.setName(name);
        person.setPhone(phone);
        person.setEmail(email);
        person.setBirthdate(birthdate);

        return personRepository.save(person);
    }

    public Company createCompany(String name, String phone, String email, String identifier ) {
        Company company = new Company();
        company.setName(name);
        company.setPhone(phone);
        company.setEmail(email);
        company.setCompanyIdentifier(identifier);

        return companyRepository.save(company);
    }
}
