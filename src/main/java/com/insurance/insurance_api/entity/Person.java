package com.insurance.insurance_api.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Person extends Client {

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

}
