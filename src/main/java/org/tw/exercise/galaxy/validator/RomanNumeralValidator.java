package org.tw.exercise.galaxy.validator;

import org.tw.exercise.galaxy.exception.InvalidRomanNumeralException;


public interface RomanNumeralValidator {

	void validate(String romanLiteral) throws InvalidRomanNumeralException;
	String name();
}
