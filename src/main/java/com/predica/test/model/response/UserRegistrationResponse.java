package com.predica.test.model.response;

import com.predica.test.model.dto.UserRegistrationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponse {
	private String uuid;
	private String message;
}
