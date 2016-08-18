package org.tw.exercise.galaxy.exception;

public class InvalidRomanNumeralException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public static final String INVALID_ROMAN = "Invalid Roman Numeral";
	
	public InvalidRomanNumeralException(String message){
		super(message);
	}
	
	public InvalidRomanNumeralException(String message, Throwable cause){
		super(message, cause);
	}
}
