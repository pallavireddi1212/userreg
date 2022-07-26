package com.predica.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.predica.test.exception.UserAlreadyRegisteredException;
import com.predica.test.model.dto.UserRegistrationDTO;
import com.predica.test.model.response.UserRegistrationResponse;

public interface IUserRegistrationService {

	public UserRegistrationResponse userRegistration(UserRegistrationDTO userRegistrationDTO) 
			throws UserAlreadyRegisteredException, JsonMappingException, JsonProcessingException;
}
