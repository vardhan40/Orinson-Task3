package com.example.demo.Exception;

public class BookExceptionHandler extends RuntimeException{
	private static final long serialVersionID = 1L;

	public BookExceptionHandler(String message) {
		super(message);
	}

}
