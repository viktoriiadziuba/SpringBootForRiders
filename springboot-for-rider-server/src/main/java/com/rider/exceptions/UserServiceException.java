package com.rider.exceptions;

public class UserServiceException extends RuntimeException {
	
	private static final long serialVersionUID = -3808208835918174748L;

	public UserServiceException(String message) {
		super(message);
	}

}
