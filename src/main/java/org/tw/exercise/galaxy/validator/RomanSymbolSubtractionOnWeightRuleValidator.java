package org.tw.exercise.galaxy.validator;

import static org.tw.exercise.galaxy.utils.RomanUtils.getRomanSymbols;

import org.tw.exercise.galaxy.exception.InvalidRomanNumeralException;
import org.tw.exercise.galaxy.utils.RomanUtils;

public class RomanSymbolSubtractionOnWeightRuleValidator implements RomanNumeralValidator {
	
	private static final int EXPECTED_SYMBOL_WEIGHT_RATIO = 10;

	private static final String NAME = "WEIGHTED_SUBTRACTION";
	
	@Override
	public void validate(String romanLiteral) throws InvalidRomanNumeralException {
		validateSymbolWeightRatio(romanLiteral);
	}
	
	@Override
	public String name() {
		return NAME;
	}

	private void validateSymbolWeightRatio( String romanLiteral ){
		char[] romanCharacters = getRomanSymbols(romanLiteral);
		for ( int count = 0; count < romanCharacters.length - 1; count++ ){
			ensureSymbolWeightRatioLtEqExpectedRatio(romanCharacters[count], romanCharacters[count+1]);
		}
	}

	private void ensureSymbolWeightRatioLtEqExpectedRatio (  Character currentCh, Character nextCh ){
		if ( !isSymbolWeightRatioLtEqExpectedRatio( currentCh, nextCh ) )
			throw new InvalidRomanNumeralException(InvalidRomanNumeralException.INVALID_ROMAN);
	}
	
	private boolean isSymbolWeightRatioLtEqExpectedRatio (  Character currentCh, Character nextCh ){
		return RomanUtils.isSymbolWeightRatioLtEqExpectedRatio(currentCh, nextCh, EXPECTED_SYMBOL_WEIGHT_RATIO);
	}
}