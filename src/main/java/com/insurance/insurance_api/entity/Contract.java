package com.insurance.insurance_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "contracts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contract_id", nullable = false)

    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @JsonIgnore
    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "cost_amount", nullable = false)
    private double costAmount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    @JoinColumn(name = "client_id")
    private Client client;




}
