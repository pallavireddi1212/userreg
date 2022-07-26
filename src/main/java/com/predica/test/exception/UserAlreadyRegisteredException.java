package com.predica.test.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyRegisteredException(String message) {
		super(message);
	}
}
