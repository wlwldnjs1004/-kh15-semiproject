package com.kh.spring09.error;


public class TargetNotFoundException extends RuntimeException{

	public TargetNotFoundException() {
		super();

	}

	public TargetNotFoundException(String message) {
		super(message);
	}

}
