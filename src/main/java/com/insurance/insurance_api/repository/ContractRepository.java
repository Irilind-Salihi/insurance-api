package com.insurance.insurance_api.repository;

import com.insurance.insurance_api.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract,Long> {
}
