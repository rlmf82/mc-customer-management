package com.mastercard.microservice.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CustomerDto extends BaseDto {

	private Long id;
	
	@NotEmpty
	@NotNull
	private String firstName;

	@NotEmpty
	@NotNull
	private String lastName;

	private List<AddressDto> addresses;
	
}