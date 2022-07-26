package com.predica.test.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredicaCustomExceptionResponse {

	private String message;
	private String details;
}
