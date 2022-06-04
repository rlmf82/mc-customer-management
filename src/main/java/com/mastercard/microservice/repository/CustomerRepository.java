package com.mastercard.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mastercard.microservice.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
}