package com.insurance.insurance_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
        name = "contracts",
        indexes = {
                @Index(name = "idx_contract_client_id", columnList = "client_id"),
                @Index(name = "idx_contract_end_date", columnList = "end_date"),
                @Index(name = "idx_contract_client_end_date", columnList = "client_id, end_date")
        }
)
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

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "cost_amount", nullable = false)
    private BigDecimal costAmount;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "client_id")
    private Client client;




}
