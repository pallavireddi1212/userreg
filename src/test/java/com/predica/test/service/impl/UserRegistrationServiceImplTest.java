package com.predica.test.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.predica.test.model.User;
import com.predica.test.model.dto.UserRegistrationDTO;
import com.predica.test.model.response.IpAddress;
import com.predica.test.model.response.UserRegistrationResponse;
import com.predica.test.repository.UserRegistrationRepository;

public class UserRegistrationServiceImplTest {

	@InjectMocks
	UserRegistrationServiceImpl userRegistrationService;
	
	@Mock
	RestTemplate restTemplate;
	
	@Mock
	ObjectMapper objMapper;
	
	@Mock
	UserRegistrationRepository userRegistrationRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void userRegistration() throws JsonMappingException, JsonProcessingException {
		UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
		userRegistrationDTO.setUserId("pallavir2");
		userRegistrationDTO.setPassword("Pallavi1#");
		userRegistrationDTO.setIpAddress("192.168.1.1");
		IpAddress ipAddress = new IpAddress();
		ipAddress.setCountry("Canada");
		ipAddress.setCity("Vancouver");
		String body = "{\"status\":\"success\",\"country\":\"Canada\",\"countryCode\":\"CA\",\"region\":\"MD\",\"regionName\":\"Maryland\",\"city\":\"Greenbelt\",\"zip\":\"20770\",\"lat\":39.0013,\"lon\":-76.888,\"timezone\":\"America/New_York\",\"isp\":\"Verizon Business\",\"org\":\"Verizon Business\",\"as\":\"AS701 Verizon Business\",\"query\":\"72.66.51.165\"}";
		ResponseEntity<String> response = mock(ResponseEntity.class);
		when(response.getBody()).thenReturn(body);
		when(objMapper.readValue(body, IpAddress.class)).thenReturn(ipAddress);
		when(restTemplate.getForEntity("http://ip-api.com/json", String.class))
				.thenReturn(response);
		User existingUser = new User();
		User user = new User();
		User savedUser = new User();
		savedUser.setUuid("c8d29100-ee05-4179-8fa2-991cdc7636e1");
		savedUser.setUserId("pallavir");
		savedUser.setPassword("Pallavi1#");
		savedUser.setIpAddress("192.168.1.1");
		UserRegistrationRepository userRegistrationRepository = mock(UserRegistrationRepository.class);
		when(userRegistrationRepository.findByUserId("pallavir2"))
		.thenReturn(existingUser);
		when(userRegistrationRepository.save(any(User.class)))
		.thenReturn(savedUser);
		
		UserRegistrationResponse userRegistrationResponse = userRegistrationService.userRegistration(userRegistrationDTO);
		assertTrue(userRegistrationResponse.getMessage().contains("pallavir2"));
	}
}
