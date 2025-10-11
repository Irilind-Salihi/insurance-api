package com.insurance.insurance_api.repository;

import com.insurance.insurance_api.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract,Long> {

    // Active contracts
    @Query("SELECT c FROM Contract c " +
            "WHERE c.client.id = :clientId " +
            "AND (c.endDate IS NULL OR c.endDate > CURRENT_DATE)")
    List<Contract> findActiveContractsByClientId(@Param("clientId") Long clientId);

    // Active contracts filtered by updateDate
    @Query("SELECT c FROM Contract c " +
            "WHERE c.client.id = :clientId " +
            "AND (c.endDate IS NULL OR c.endDate > CURRENT_DATE) " +
            "AND c.updateDate = :updateDate")
    List<Contract> findActiveContractsByClientIdAndUpdateDate(
            @Param("clientId") Long clientId,
            @Param("updateDate") LocalDate updateDate
    );

}
