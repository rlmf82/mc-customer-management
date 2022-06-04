package com.mastercard.microservice;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.microservice.dto.AddressDto;
import com.mastercard.microservice.dto.CustomerDto;
import com.mastercard.microservice.services.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private CustomerService serviceMock;
	
	@Value("classpath:requests/new_customer.json")
	private Resource getNewCustomerRequestJson;

	@Value("classpath:requests/new_address.json")
	private Resource getNewAddressRequestJson;
	
	@Value("classpath:responses/new_customer.json")
	private Resource getNewCustomerResponseJson;
	
	@Value("classpath:responses/retrieve_customer.json")
	private Resource getRetrieveCustomerResponseJson;
	
	@Value("classpath:responses/retrieve_customer_addresses.json")
	private Resource getRetrieveCustomerAddressesResponseJson;

	@Value("classpath:responses/new_address.json")
	private Resource getNewAddressResponseJson;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void insertCustomer() throws Exception {

		CustomerDto response = mapper.readValue(getNewCustomerResponseJson.getInputStream(), CustomerDto.class);
		
        String request = IOUtils.toString(getNewCustomerRequestJson.getInputStream(), StandardCharsets.UTF_8);

        Mockito.when(serviceMock.save(Mockito.any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/customer")
                            .content(request)
                            .with(new RequestPostProcessor() {
                                @Override
                                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                                    return request;
                                }
                            })
                            .contentType(MediaType.APPLICATION_JSON))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(response.getId()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(response.getFirstName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(response.getLastName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].name").value(response.getAddresses().get(0).getName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].number").value(response.getAddresses().get(0).getNumber()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(response.getAddresses().get(0).getState()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(response.getAddresses().get(0).getCity()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].zipCode").value(response.getAddresses().get(0).getZipCode()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].country").value(response.getAddresses().get(0).getCountry()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].type").value(response.getAddresses().get(0).getType().toString()));

        Mockito.verify(serviceMock, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void getCustomer() throws Exception {
		Long id = 38L;
		CustomerDto response = mapper.readValue(getRetrieveCustomerResponseJson.getInputStream(), CustomerDto.class);
		
        Mockito.when(serviceMock.retrieve(id)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                            .get("/customer/"+id)
                            .with(new RequestPostProcessor() {
                                @Override
                                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                                    return request;
                                }
                            })
                            .contentType(MediaType.APPLICATION_JSON))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(response.getId()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(response.getFirstName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(response.getLastName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].name").value(response.getAddresses().get(0).getName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].number").value(response.getAddresses().get(0).getNumber()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(response.getAddresses().get(0).getState()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(response.getAddresses().get(0).getCity()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].zipCode").value(response.getAddresses().get(0).getZipCode()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].country").value(response.getAddresses().get(0).getCountry()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].type").value(response.getAddresses().get(0).getType().toString()));

        Mockito.verify(serviceMock, Mockito.times(1)).retrieve(id);
	}
	
	@Test
	public void getCustomerAddresses() throws Exception {
		Long id = 38L;
		List<AddressDto> response = mapper.readValue(getRetrieveCustomerAddressesResponseJson.getInputStream(), new TypeReference<>(){});
		
        Mockito.when(serviceMock.retrieveAddresses(id)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                            .get("/customer/"+id+"/address")
                            .with(new RequestPostProcessor() {
                                @Override
                                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                                    return request;
                                }
                            })
                            .contentType(MediaType.APPLICATION_JSON))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(response.get(0).getName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].number").value(response.get(0).getNumber()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].state").value(response.get(0).getState()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].city").value(response.get(0).getCity()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].zipCode").value(response.get(0).getZipCode()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].country").value(response.get(0).getCountry()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].type").value(response.get(0).getType().toString()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value(response.get(1).getName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].number").value(response.get(1).getNumber()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].unit").value(response.get(1).getUnit()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].state").value(response.get(1).getState()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].city").value(response.get(1).getCity()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].zipCode").value(response.get(1).getZipCode()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].country").value(response.get(1).getCountry()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].type").value(response.get(1).getType().toString()));

        Mockito.verify(serviceMock, Mockito.times(1)).retrieveAddresses(id);
	}
	
	@Test
	public void insertCustomerAddress() throws Exception {
		Long id = 20L;
		CustomerDto response = mapper.readValue(getNewAddressResponseJson.getInputStream(), CustomerDto.class);
		
		String request = IOUtils.toString(getNewAddressRequestJson.getInputStream(), StandardCharsets.UTF_8);
		
        Mockito.when(serviceMock.insertAddress(Mockito.any(), Mockito.any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                            .patch("/customer/"+id+"/address")
                            .content(request)
                            .with(new RequestPostProcessor() {
                                @Override
                                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                                    return request;
                                }
                            })
                            .contentType(MediaType.APPLICATION_JSON))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(response.getId()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(response.getFirstName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(response.getLastName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].name").value(response.getAddresses().get(0).getName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].number").value(response.getAddresses().get(0).getNumber()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].state").value(response.getAddresses().get(0).getState()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(response.getAddresses().get(0).getCity()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].zipCode").value(response.getAddresses().get(0).getZipCode()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].country").value(response.getAddresses().get(0).getCountry()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].type").value(response.getAddresses().get(0).getType().toString()));

        Mockito.verify(serviceMock, Mockito.times(1)).insertAddress(Mockito.any(), Mockito.any());
	}	
}