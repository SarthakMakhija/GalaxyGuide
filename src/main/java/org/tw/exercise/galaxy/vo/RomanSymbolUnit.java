package org.tw.exercise.galaxy.vo;

import java.util.Arrays;
import java.util.List;

public class RomanSymbolUnit implements Comparable<RomanSymbolUnit> {

	private String symbol;
	
	private Integer weight;

	public RomanSymbolUnit(String unit) {
		super();
		this.symbol = unit.toUpperCase();
		this.weight = getSymbolWeight(unit);
	}
	
	private RomanSymbolUnit(String unit, int weight){
		this.symbol = unit.toUpperCase();
		this.weight = weight;
	}
	
	public static final RomanSymbolUnit M = new RomanSymbolUnit("M", 1000);
	public static final RomanSymbolUnit D = new RomanSymbolUnit("D", 500);
	public static final RomanSymbolUnit C = new RomanSymbolUnit("C", 100);
	public static final RomanSymbolUnit L = new RomanSymbolUnit("L", 50);
	public static final RomanSymbolUnit X = new RomanSymbolUnit("X", 10);
	public static final RomanSymbolUnit V = new RomanSymbolUnit("V", 5);
	public static final RomanSymbolUnit I = new RomanSymbolUnit("I", 1);
	
	private static final RomanSymbolUnit CM = new RomanSymbolUnit("CM", 900);
	private static final RomanSymbolUnit CD = new RomanSymbolUnit("CD", 400);
	private static final RomanSymbolUnit XC = new RomanSymbolUnit("XC", 90);
	private static final RomanSymbolUnit XL = new RomanSymbolUnit("XL", 40);
	private static final RomanSymbolUnit IX = new RomanSymbolUnit("IX", 9);
	private static final RomanSymbolUnit IV = new RomanSymbolUnit("IV", 4);
	
	private static final List<Integer> weights = Arrays.asList(
			M.weight(), CM.weight(),
			D.weight(), CD.weight(),
			C.weight(), XC.weight(),
			L.weight(), XL.weight(),
			X.weight(), IX.weight(),
			V.weight(), IV.weight(),
			I.weight());

	private static final List<String> symbols = Arrays.asList(
			M.symbol, CM.symbol, 
			D.symbol, CD.symbol, 
			C.symbol, XC.symbol, 
			L.symbol, XL.symbol, 
			X.symbol, IX.symbol, 
			V.symbol, IV.symbol, 
			I.symbol);

	public boolean isSymbolWeightRatioLtEqExpectedRatio ( RomanSymbolUnit nextSymbol, Integer expectedRatio ){
		return ( getSymbolWeight(nextSymbol.symbol) / getSymbolWeight(this.symbol) ) <= expectedRatio;
	}

	public int weight() {
		return weight; // this.getSymbolWeight(this.asString());
	}
	
	public String asString(){
		return symbol;
	}
	
	private Integer getSymbolWeight(String symbol) {
		return weights.get(symbols.indexOf(symbol.toUpperCase()));
	}

	public static boolean isRomanNumeral ( String symbol ){
		return symbols.contains(symbol.toUpperCase());
	}

	public static String convertToRoman(int decimal) {
		return toRoman(decimal, new StringBuilder());
	}
	
	private static String toRoman(int decimal, StringBuilder roman) {
		for (int i = 0; i < weights.size(); i++) {
			while (decimal >= weights.get(i)) {
				roman.append(symbols.get(i));
				decimal -= weights.get(i);
			}
		}
		return roman.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RomanSymbolUnit other = (RomanSymbolUnit) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TokenUnit [unit=" + symbol + "]";
	}

	@Override
	public int compareTo(RomanSymbolUnit unit) {
		return this.getSymbolWeight(this.symbol).compareTo(this.getSymbolWeight(unit.symbol));
	}
}