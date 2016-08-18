package org.tw.exercise.galaxy.validator;

import static org.tw.exercise.galaxy.utils.RomanUtils.isRepeatCountGreaterThanExpected;

import org.tw.exercise.galaxy.exception.InvalidRomanNumeralException;
import org.tw.exercise.galaxy.vo.RomanSymbolUnit;

public class RomanSymbolNoRepeatRuleValidator implements RomanNumeralValidator {

	private static final String NAME = "NO_REPEAT";
	
	@Override
	public void validate(String romanLiteral) throws InvalidRomanNumeralException {
		ensureNoMoreThanSingleOccurrence(romanLiteral, RomanSymbolUnit.D, RomanSymbolUnit.L, RomanSymbolUnit.V);
	}

	@Override
	public String name() {
		return NAME;
	}
	
	private void ensureNoMoreThanSingleOccurrence (String romanLiteral, RomanSymbolUnit... symbols) {
		for ( RomanSymbolUnit symbol : symbols ){
			if ( isRepeatCountGreaterThanExpected(romanLiteral, symbol.asString(), 1) ) throw new InvalidRomanNumeralException(InvalidRomanNumeralException.INVALID_ROMAN);
		}
	}
}