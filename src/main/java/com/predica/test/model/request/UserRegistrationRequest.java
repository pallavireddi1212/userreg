package com.predica.test.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.predica.test.utility.ValidPassword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
	@NotBlank(message = "Username cannot be blank.")
	@NotNull(message = "Username cannot be null.")
	private String userId;
	
	@NotBlank(message = "Password cannot be blank.")
	@NotNull(message = "Password cannot be null.")
	@Size(min=8, message = "Password need to be greater than 8 characters.")
	@ValidPassword
	private String password;
	
	@NotBlank(message = "IpAddress cannot be blank.")
	@NotNull(message = "IpAddress cannot be null.")
	private String ipAddress;
}
