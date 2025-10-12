package com.insurance.insurance_api.repository;

import com.insurance.insurance_api.entity.Contract;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract,Long> {

    // Active contracts
    @Query("""
    SELECT c
    FROM Contract c
    WHERE c.client.id = :clientId
      AND (c.endDate IS NULL OR c.endDate > CURRENT_DATE)
    """)
    List<Contract> findActiveContractsByClientId(@Param("clientId") Long clientId);

    // Active contracts filtered by updateDate
    @Query("""
    SELECT c
    FROM Contract c
    WHERE c.client.id = :clientId
      AND (c.endDate IS NULL OR c.endDate > CURRENT_DATE)
      AND c.updateDate = :updateDate
    """)
    List<Contract> findActiveContractsByClientIdAndUpdateDate(
            @Param("clientId") Long clientId,
            @Param("updateDate") LocalDate updateDate
    );

    @Query("""
    SELECT COALESCE(SUM(c.costAmount), 0)
    FROM Contract c
    WHERE c.client.id = :clientId
      AND (c.endDate IS NULL OR c.endDate > CURRENT_DATE)
    """)
    BigDecimal sumActiveContractsForClient(@Param("clientId") Long clientId);

    @Modifying
    @Transactional
    @Query("UPDATE Contract c " +
            "SET c.endDate = CURRENT_DATE, c.client = NULL " +
            "WHERE c.client.id = :clientId")
    void endAllContractsForClient(@Param("clientId") Long clientId);

}
