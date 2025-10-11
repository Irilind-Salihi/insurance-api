package com.insurance.insurance_api.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Company extends Client {

    @Column(name = "company_identifier", nullable = false)
    private String companyIdentifier;
}
