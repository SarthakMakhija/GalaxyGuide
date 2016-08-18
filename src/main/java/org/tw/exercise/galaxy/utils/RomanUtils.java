package org.tw.exercise.galaxy.utils;

import org.tw.exercise.galaxy.vo.RomanSymbolUnit;

public class RomanUtils {

	public static final String CREDIT_IDENTIFIER = "credits";
	
	private RomanUtils(){
	}
	
	public static boolean isCurrentSymbolSmallerEqualsNext ( Character currentCh, Character nextCh ) {
		if ( isCurrentSymbolEqualsNext( currentCh, nextCh ) || isCurrentSymbolSmallerThanNext(currentCh, nextCh) ) return true;
		return false;
	}
	
	public static boolean isCurrentSymbolEqualsNext ( Character currentCh, Character nextCh ) {
		return new RomanSymbolUnit(asString(currentCh)).compareTo(new RomanSymbolUnit(asString(nextCh))) == 0 ? true : false;
	}
	
	public static boolean isCurrentSymbolSmallerThanNext ( Character currentCh, Character nextCh ) {
		return new RomanSymbolUnit(asString(currentCh)).compareTo(new RomanSymbolUnit(asString(nextCh))) == -1 ? true : false;
	}
	
	public static boolean isCurrentSymbolSmallerThanNext ( RomanSymbolUnit unit, RomanSymbolUnit nextUnit ) {
		return unit.compareTo(nextUnit) == -1 ? true : false;
	}
	
	public static boolean isCurrentSymbolLargerThanNext ( Character currentCh, Character nextCh ) {
		return new RomanSymbolUnit(asString(currentCh)).compareTo(new RomanSymbolUnit(asString(nextCh))) == 1 ? true : false;
	}
	
	public static boolean isRepeatCountGreaterThanExpected (  String romanLiteral, String symbol, Integer expectedCount ){
		return getRepeatCount(romanLiteral, symbol).compareTo(expectedCount) == 1 ? true : false;
	}
	
	public static Integer getRepeatCount ( String romanLiteral, String symbol ){
		return romanLiteral.length() - romanLiteral.replaceAll(symbol, "").length();
	}
	
	public static char[] getRomanSymbols( String romanLiteral ){
		return romanLiteral.toCharArray();
	}
	
	public static boolean isSymbolWeightRatioLtEqExpectedRatio ( Character currentCh, Character nextCh, Integer expectedRatio ){
		return 
				new RomanSymbolUnit(asString(currentCh)).isSymbolWeightRatioLtEqExpectedRatio(new RomanSymbolUnit(asString(nextCh)), expectedRatio);
	}
	
	public static String[] split ( String input, String splitOn ){
		return input.split(splitOn);
	}
	
	public static String[] splitOnSpace ( String input ) {
		return split(input, " ");
	}
	
	public static boolean isRomanNumeral ( String symbol ){
		return RomanSymbolUnit.isRomanNumeral(symbol);
	}
	
	public static String asString ( char ch ){
		return String.valueOf(ch);
	}
	
	public static String asString ( Integer i ){
		return String.valueOf(i);
	}

}