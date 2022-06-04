package com.mastercard.microservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.mastercard.microservice.entities.AddressType;

import lombok.Data;

@Data
public class AddressDto extends BaseDto {
	
	@NotEmpty
	@NotNull
	private String name;

	@NotEmpty
	@NotNull
	private String number;

	private String unit;

	@NotEmpty
	@NotNull
	private String state;

	@NotEmpty
	@NotNull
	private String city;

	@NotEmpty
	@NotNull
	private String zipCode;

	@NotEmpty
	@NotNull
	private String country;

	@NotNull
	private AddressType type;
	
}