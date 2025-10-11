package com.insurance.insurance_api.repository;

import com.insurance.insurance_api.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
