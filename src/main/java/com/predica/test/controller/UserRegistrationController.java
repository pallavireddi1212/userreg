package com.predica.test.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.predica.test.exception.PredicaException;
import com.predica.test.model.dto.UserRegistrationDTO;
import com.predica.test.model.request.UserRegistrationRequest;
import com.predica.test.model.response.UserRegistrationResponse;
import com.predica.test.service.impl.UserRegistrationServiceImpl;
import com.predica.test.utility.ValidationUtility;

@RestController
@RequestMapping("/api")
public class UserRegistrationController {

	@Autowired
	private UserRegistrationServiceImpl userRegistrationService;

	@Autowired
	private ValidationUtility validationUtility;

	@PostMapping(path = "/userregistration", consumes = "application/json", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> userRegistration(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest,
			BindingResult bindingResult) {
		// Validating the request
		ResponseEntity<?> validateRequest = this.validationUtility.validateRequest(bindingResult);
		if (validateRequest != null)
			return validateRequest;

		UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
		BeanUtils.copyProperties(userRegistrationRequest, userRegistrationDTO);
		UserRegistrationResponse userRegistrationResponse = null;
		try {
			userRegistrationResponse = userRegistrationService.userRegistration(userRegistrationDTO);
			return new ResponseEntity<UserRegistrationResponse>(userRegistrationResponse, HttpStatus.CREATED);
		} catch(Exception e) {
			throw new PredicaException(e.getMessage());
		}
	}

}
