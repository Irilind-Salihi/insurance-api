package com.insurance.insurance_api.utils;

import com.insurance.insurance_api.entity.Client;
import com.insurance.insurance_api.entity.Company;
import com.insurance.insurance_api.entity.Contract;
import com.insurance.insurance_api.entity.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

public class MockValue {

    // ---------------- Client ----------------
    public static Client mockClient1() {
        Client client = new Client();
        client.setId(1L);
        client.setName("Mock Client 1");
        client.setEmail("client1@example.com");
        client.setPhone("+111111111");
        client.setContracts(Collections.emptyList());
        return client;
    }

    public static Client mockClient2() {
        Client client = new Client();
        client.setId(2L);
        client.setName("Mock Client 2");
        client.setEmail("client2@example.com");
        client.setPhone("+222222222");
        client.setContracts(Collections.emptyList());
        return client;
    }

    // ---------------- Person ----------------
    public static Person mockPerson1() {
        Person person = new Person();
        person.setId(1L);
        person.setName("Mock Person 1");
        person.setEmail("person1@example.com");
        person.setPhone("+111111111");
        person.setBirthdate(LocalDate.of(1990, 1, 1));
        person.setContracts(Collections.emptyList());
        return person;
    }

    public static Person mockPerson2() {
        Person person = new Person();
        person.setId(2L);
        person.setName("Mock Person 2");
        person.setEmail("person2@example.com");
        person.setPhone("+222222222");
        person.setBirthdate(LocalDate.of(1995, 5, 5));
        person.setContracts(Collections.emptyList());
        return person;
    }

    // ---------------- Company ----------------
    public static Company mockCompany1() {
        Company company = new Company();
        company.setId(1L);
        company.setName("Mock Company 1");
        company.setEmail("company1@example.com");
        company.setPhone("+111111111");
        company.setCompanyIdentifier("ABC-1");
        company.setContracts(Collections.emptyList());
        return company;
    }

    public static Company mockCompany2() {
        Company company = new Company();
        company.setId(2L);
        company.setName("Mock Company 2");
        company.setEmail("company2@example.com");
        company.setPhone("+222222222");
        company.setCompanyIdentifier("XYZ-2");
        company.setContracts(Collections.emptyList());
        return company;
    }


    // ---------------- Contract ----------------
    public static Contract mockContract1() {
        Contract contract = new Contract();
        contract.setId(1L);
        contract.setClient(mockClient1());
        contract.setCostAmount(new BigDecimal("500"));
        contract.setStartDate(LocalDate.of(2025, 10, 1));
        contract.setUpdateDate(LocalDate.of(2025, 10, 1));
        contract.setEndDate(null);
        return contract;
    }

    public static Contract mockContract2() {
        Contract contract = new Contract();
        contract.setId(2L);
        contract.setClient(mockClient1());
        contract.setCostAmount(new BigDecimal("1000"));
        contract.setStartDate(LocalDate.of(2025, 9, 1));
        contract.setUpdateDate(LocalDate.of(2025, 9, 1));
        contract.setEndDate(null);
        return contract;
    }
}
