package com.mastercard.microservice.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mastercard.microservice.dto.AddressDto;
import com.mastercard.microservice.dto.CustomerDto;
import com.mastercard.microservice.entities.Address;
import com.mastercard.microservice.entities.Customer;
import com.mastercard.microservice.exception.AddressAlreadyExistsException;
import com.mastercard.microservice.exception.CustomerNotFoundException;
import com.mastercard.microservice.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{

	ModelMapper mapper;
	
	CustomerRepository repository;
	
	@Override
	public CustomerDto save(CustomerDto customerDto) {

		Customer customer = mapper.map(customerDto, Customer.class);
		
		if(customer.getAddresses() != null) {
			customer.getAddresses().forEach(a -> a.setCustomer(customer));
		}
		
		return mapper.map(repository.save(customer), CustomerDto.class);
	}

	@Override
	public CustomerDto retrieve(Long id) {
		
		Optional<Customer> customer = repository.findById(id);
		
		if(!customer.isPresent()) {
			throw new CustomerNotFoundException();
		}
		
		return mapper.map(customer.get(), CustomerDto.class);
	}
	
	@Override
	public List<AddressDto> retrieveAddresses(Long id) {
		
		CustomerDto customer = retrieve(id);
		
		if(customer.getAddresses() != null) {
			return customer.getAddresses();	
		}
		
		return Collections.emptyList();
	}

	@Override
	public CustomerDto insertAddress(Long id, AddressDto address) {

		Optional<Customer> customer = repository.findById(id);
		
		if(!customer.isPresent()) {
			throw new CustomerNotFoundException();
		}

		Customer updatedCustomer = customer.get();
		Address newAddress = mapper.map(address, Address.class);
		newAddress.setCustomer(updatedCustomer);
		
		if(updatedCustomer.getAddresses().stream().anyMatch(a -> a.equals(newAddress))) {
			throw new AddressAlreadyExistsException();
		}
		
		updatedCustomer.getAddresses().add(newAddress);
		
		return mapper.map(repository.save(updatedCustomer), CustomerDto.class);
	}
}