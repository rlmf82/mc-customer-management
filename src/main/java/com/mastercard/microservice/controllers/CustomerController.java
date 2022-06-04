package com.mastercard.microservice.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.microservice.dto.AddressDto;
import com.mastercard.microservice.dto.CustomerDto;
import com.mastercard.microservice.services.CustomerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
	
	private CustomerService service;

	@PostMapping()
	public ResponseEntity<CustomerDto> insertCustomer(@Valid @RequestBody CustomerDto request) {
		return ResponseEntity.ok(service.save(request));		
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> retrieveCustomer(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(service.retrieve(id));
	}

	@GetMapping("/{id}/address")
	public ResponseEntity<List<AddressDto>> retrieveCustomerAddresses(@PathVariable(name = "id") Long id){
		return ResponseEntity.ok(service.retrieveAddresses(id));
	}
	
	@PatchMapping("/{id}/address")
	public ResponseEntity<CustomerDto> InsertAddress(@Valid @RequestBody AddressDto addresses, @PathVariable(name = "id") Long id){
		return ResponseEntity.ok(service.insertAddress(id, addresses));
	}
}