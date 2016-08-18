package org.tw.exercise.galaxy.validator;

import static org.tw.exercise.galaxy.utils.RomanUtils.getRomanSymbols;
import static org.tw.exercise.galaxy.utils.RomanUtils.asString;
import static org.tw.exercise.galaxy.utils.RomanUtils.isCurrentSymbolLargerThanNext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tw.exercise.galaxy.exception.InvalidRomanNumeralException;
import org.tw.exercise.galaxy.utils.RomanUtils;
import org.tw.exercise.galaxy.vo.RomanSymbolUnit;

public class RomanSymbolRepeatCountRuleValidator implements RomanNumeralValidator {

	private static final String NAME = "REPEAT_WITH_COUNT";
	
	private static final List<String> symbolsToValidateForRepeatCount = new ArrayList<String>(){{
		add(RomanSymbolUnit.I.asString());
		add(RomanSymbolUnit.C.asString());
		add(RomanSymbolUnit.X.asString());
		add(RomanSymbolUnit.M.asString());
	}};

	private static final int EXPECTED_REPEAT_COUNT = 4  ;
	
	private List<RomanSymbolRepeat> repeatCounts = new ArrayList<RomanSymbolRepeat>();
	
	public RomanSymbolRepeatCountRuleValidator(){
		initializeRepeatCounts();
	}
	
	private void initializeRepeatCounts(){
		for ( int count = 0; count < symbolsToValidateForRepeatCount.size(); count++ ) {
			repeatCounts.add(new RomanSymbolRepeat(0, false));	
		}
	}
	
	@Override
	public void validate(String romanLiteral) throws InvalidRomanNumeralException {
		storeSymbolRepeatCounts(romanLiteral);
		ensureNoMoreThanExpectedRepeatCount();
	}

	@Override
	public String name() {
		return NAME;
	}

	private void storeSymbolRepeatCounts( String romanLiteral ){
		char[] romanCharacters = getRomanSymbols(romanLiteral);
		
		for ( int count = 0; count < romanCharacters.length; count++ ) {
			if ( shouldValidateSymbolCount(romanCharacters[count]) ){
				incrementRepeatCountOfSymbolAtIndex(indexOfSymbol(romanCharacters[count]));

				if ( (count+1) < romanCharacters.length && isCurrentSymbolLargerThanNext(romanCharacters[count], romanCharacters[count+1]) ){
					markWithSmallerSymbolSucceedAtIndex(indexOfSymbol(romanCharacters[count]));
				}
			}
		}
	}

	private void ensureNoMoreThanExpectedRepeatCount() {
		Collections.sort(repeatCounts);
		validateRepeatCountAgainstExepcted();
	}
	
	private void validateRepeatCountAgainstExepcted(){
		for ( RomanSymbolRepeat r : repeatCounts ){
			if ( r.isRepeatCountGtThanExpected() || r.isRepeatCountEqExepctedWithoutSmallerSymbolSucceed() ){
				throw new InvalidRomanNumeralException(InvalidRomanNumeralException.INVALID_ROMAN);
			}
		}
	}

	private boolean shouldValidateSymbolCount ( char ch ) {
		return symbolsToValidateForRepeatCount.contains(asString(ch));
	}
	
	private int indexOfSymbol ( char ch ){
		return symbolsToValidateForRepeatCount.indexOf(asString(ch));
	}
	
	private void incrementRepeatCountOfSymbolAtIndex( int index ){
		repeatCounts.set(index, repeatCounts.get(index).incrementRepeatCount());
	}

	private void markWithSmallerSymbolSucceedAtIndex ( int index ){
		repeatCounts.set(index, repeatCounts.get(index).markSmallerSymbolSucceedd());
	}
	
	private static class RomanSymbolRepeat implements Comparable<RomanSymbolRepeat> {
		
		private Integer repeatCount;
		
		private boolean withSmallerSymbolSucceed;
		
		public RomanSymbolRepeat ( Integer repeatCount, boolean withSmallerSymbolSucceed ){
			this.repeatCount = repeatCount;
			this.withSmallerSymbolSucceed = withSmallerSymbolSucceed;
		}

		public RomanSymbolRepeat incrementRepeatCount(){
			this.repeatCount = this.repeatCount + 1;
			return this;
		}

		public RomanSymbolRepeat markSmallerSymbolSucceedd(){
			this.withSmallerSymbolSucceed = true;
			return this;
		}
		
		public boolean isRepeatCountGtThanExpected( ){
			return this.repeatCount > EXPECTED_REPEAT_COUNT ;
		}

		public boolean isRepeatCountEqExepctedWithoutSmallerSymbolSucceed( ){
			return this.repeatCount == EXPECTED_REPEAT_COUNT && !this.withSmallerSymbolSucceed;
		}
		
		@Override
		public int compareTo(RomanSymbolRepeat symbolRepeat) {
			return repeatCount.compareTo(symbolRepeat.repeatCount);
		}
	}
}