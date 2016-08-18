package org.tw.exercise.galaxy.validator;

import static org.tw.exercise.galaxy.utils.RomanUtils.isCurrentSymbolSmallerThanNext;
import static org.tw.exercise.galaxy.utils.RomanUtils.getRomanSymbols;

import org.tw.exercise.galaxy.exception.InvalidRomanNumeralException;

public class RomanSymbolSingleSubtractionRuleValidator implements RomanNumeralValidator{

	private int misplacedSymbolCount = 0;

	private static final String NAME = "SINGLE_SUBTRACTION";
	
	@Override
	public void validate(String romanLiteral) throws InvalidRomanNumeralException {
		expectNoMoreThanOneSingleValueSubtractionFromLargeValue(romanLiteral);
	}

	@Override
	public String name() {
		return NAME;
	}

	private void expectNoMoreThanOneSingleValueSubtractionFromLargeValue( String romanLiteral ){
		char[] romanCharacters = getRomanSymbols(romanLiteral);
		
		for ( int count = 0; count < romanCharacters.length - 1; count++ ) {
			if ( isCurrentSymbolSmallerThanNext(romanCharacters[count], romanCharacters[count + 1]) ) incrementMisplacedSymbolCount();
			else																						resetMisplacedSymbolCount();
			
			ensureMisplacedCharacterCountLessThanOne();
		}
	}
	
	private void ensureMisplacedCharacterCountLessThanOne(){
		if ( misplacedSymbolCount > 1 ) throw new InvalidRomanNumeralException(InvalidRomanNumeralException.INVALID_ROMAN);
	}
	
	private void incrementMisplacedSymbolCount() {
		misplacedSymbolCount++;
	}
	
	private void resetMisplacedSymbolCount(){
		misplacedSymbolCount = 0;
	}
}