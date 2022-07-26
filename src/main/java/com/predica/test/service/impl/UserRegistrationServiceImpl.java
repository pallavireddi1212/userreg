package com.predica.test.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.predica.test.exception.NotEligibleException;
import com.predica.test.exception.UserAlreadyRegisteredException;
import com.predica.test.model.User;
import com.predica.test.model.dto.UserRegistrationDTO;
import com.predica.test.model.response.IpAddress;
import com.predica.test.model.response.UserRegistrationResponse;
import com.predica.test.repository.UserRegistrationRepository;
import com.predica.test.service.IUserRegistrationService;

import java.util.UUID;

@Service
public class UserRegistrationServiceImpl implements IUserRegistrationService {

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;

	@Autowired
	private RestTemplate restTemplate;

	public UserRegistrationResponse userRegistration(UserRegistrationDTO userRegistrationDTO)
			throws JsonMappingException, JsonProcessingException {
		User existingUser = null;
		User user = null;
		UserRegistrationResponse userRegistrationResponse = null;
		IpAddress ipAddress = null;

		ResponseEntity<String> response = restTemplate.getForEntity("http://ip-api.com/json", String.class);
		if (response.getBody().contains("country")) {
			ObjectMapper objMapper = new ObjectMapper();
			ipAddress = objMapper.readValue(response.getBody(), IpAddress.class);
//			ipAddress.setCountry("Canada");
			if (!"Canada".equals(ipAddress.getCountry())) {
				throw new NotEligibleException("As IP doesn't belong to Canada, " + userRegistrationDTO.getUserId()
						+ " cannot be registered.");
			}
		}

		if (userRegistrationDTO.getUserId() != null)
			existingUser = this.userRegistrationRepository.findByUserId(userRegistrationDTO.getUserId());

		if (existingUser != null)
			throw new UserAlreadyRegisteredException(userRegistrationDTO.getUserId() + " already registered.");
		else {
			user = new User();
			userRegistrationResponse = new UserRegistrationResponse();
			BeanUtils.copyProperties(userRegistrationDTO, user);
			UUID uuid = UUID.randomUUID();
			user.setUuid(uuid.toString());
			User savedUser = this.userRegistrationRepository.save(user);
			userRegistrationResponse.setUuid(uuid.toString());
			userRegistrationResponse.setMessage("user with " + userRegistrationDTO.getUserId() + " as username residing in "
					+ ipAddress.getCity() + " is registered successfully.");
		}

		return userRegistrationResponse;
	}
}
