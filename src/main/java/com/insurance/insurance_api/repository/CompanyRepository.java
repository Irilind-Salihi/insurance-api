package com.insurance.insurance_api.repository;

import com.insurance.insurance_api.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
