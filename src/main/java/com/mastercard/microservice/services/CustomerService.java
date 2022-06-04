package com.mastercard.microservice.services;

import java.util.List;

import com.mastercard.microservice.dto.AddressDto;
import com.mastercard.microservice.dto.CustomerDto;

public interface CustomerService {

	public CustomerDto save(CustomerDto customer);
	
	public CustomerDto retrieve(Long id);
	
	public List<AddressDto> retrieveAddresses(Long id);
	
	public CustomerDto insertAddress(Long id, AddressDto addresses);
	
}