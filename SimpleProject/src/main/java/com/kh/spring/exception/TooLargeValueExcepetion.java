package com.kh.spring.exception;

public class TooLargeValueExcepetion extends RuntimeException {
	public TooLargeValueExcepetion(String message) {
		super(message);
	}
}
