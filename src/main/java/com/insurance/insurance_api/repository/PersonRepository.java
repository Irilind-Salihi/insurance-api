package com.insurance.insurance_api.repository;

import com.insurance.insurance_api.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {
}
