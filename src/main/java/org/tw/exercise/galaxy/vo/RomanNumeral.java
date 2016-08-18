package org.tw.exercise.galaxy.vo;


import static org.tw.exercise.galaxy.utils.RomanUtils.isCurrentSymbolSmallerThanNext;
import static org.tw.exercise.galaxy.vo.RomanSymbolUnit.convertToRoman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tw.exercise.galaxy.exception.InvalidRomanNumeralException;
import org.tw.exercise.galaxy.validator.RomanNumeralValidator;
import org.tw.exercise.galaxy.validator.RomanSymbolNoRepeatRuleValidator;
import org.tw.exercise.galaxy.validator.RomanSymbolRepeatCountRuleValidator;
import org.tw.exercise.galaxy.validator.RomanSymbolSingleSubtractionRuleValidator;
import org.tw.exercise.galaxy.validator.RomanSymbolSubtractionOnWeightRuleValidator;

public class RomanNumeral {

	private String romanLiteral 	= null;

	private int decimalEquivalent	= 0;
	
	private Set<RomanNumeralValidator> validators 	= new HashSet<>();

	private List<RomanSymbolUnit> 		symbols 	= new ArrayList<>(); 

	public RomanNumeral ( int numericValue ){
		this.decimalEquivalent = numericValue;
	}
	
	public  RomanNumeral (){
		withValidator(new RomanSymbolNoRepeatRuleValidator());
		withValidator(new RomanSymbolRepeatCountRuleValidator());
		withValidator(new RomanSymbolSingleSubtractionRuleValidator());
		withValidator(new RomanSymbolSubtractionOnWeightRuleValidator());	
	}
	
	public RomanNumeral addRomanSymbol ( RomanSymbolUnit unit ){
		if ( unit != null ) symbols.add(unit);
		return this;
	}
	
	public RomanNumeral withValidator ( RomanNumeralValidator romanNumeralValidator ) {
		validators.add(romanNumeralValidator);
		return this;
	}
	
	public Integer convertRomanNumeralToNumber(){
		setRomanLiteral();
		validate();
		return convertToNumber();
	}

	public String convertNumberToRoman (){
		return convertToRoman(decimalEquivalent);
	}
	
	private Integer convertToNumber () {
		if ( isEmpty() ) return null;
		for ( int count = 0; count < symbols.size() - 1; count++ ) {
			if ( isCurrentSymbolSmallerThanNext(symbols.get(count), symbols.get(count + 1)) )
				decrementDecimalValue(symbols.get(count));
			else
				incrementDecimalValue(symbols.get(count));
		}
		incrementDecimalValue(symbols.get(symbols.size() - 1));
		return decimalEquivalent;
	}
	
	
	private void validate () throws InvalidRomanNumeralException{
		for ( RomanNumeralValidator validator : validators ){
			validator.validate(romanLiteral);
		}
	}

	private boolean isEmpty() {
		return symbols.isEmpty();
	}

	private void decrementDecimalValue ( RomanSymbolUnit unit ) {
		decimalEquivalent-= getRomanNumericValue(unit);
	}

	private void incrementDecimalValue ( RomanSymbolUnit unit ) {
		decimalEquivalent+= getRomanNumericValue(unit);
	}

	private int getRomanNumericValue ( RomanSymbolUnit unit ){
		return unit.weight();
	}
	
	private void setRomanLiteral () {
		romanLiteral = combineRomanSymbolUnits();
	}
	
	private String combineRomanSymbolUnits(){
		StringBuilder sb = new StringBuilder();
		for ( RomanSymbolUnit u : symbols ){
			sb.append(getRomanSymbolRepresentation(u));
		}
		return sb.toString();
	}
	
	private String getRomanSymbolRepresentation( RomanSymbolUnit unit ){
		return unit.asString();
	}
}