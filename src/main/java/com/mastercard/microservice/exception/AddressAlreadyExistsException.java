package com.mastercard.microservice.exception;

public class AddressAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AddressAlreadyExistsException() {
		super("This address is already registered to this customer");
	}
	
}