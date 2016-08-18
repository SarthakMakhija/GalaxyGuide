package org.tw.exercise.galaxy.kbase;

import org.tw.exercise.galaxy.utils.RomanUtils;
import org.tw.exercise.galaxy.vo.RomanNumeral;
import org.tw.exercise.galaxy.vo.RomanSymbolUnit;

public class Information  {

	private String romanSymbol = "";
	
	private RomanNumeral roman = new RomanNumeral();

	private Integer unit = 1;
	
	private Integer credit = 1;
	
	public Information(String romanSymbol) {
		this.romanSymbol = romanSymbol.toUpperCase();
		roman.addRomanSymbol(new RomanSymbolUnit(romanSymbol));
	}

	public Information withCredit( Integer credit ){
		this.credit = credit;
		return this;
	}
	
	public Information withUnit(Integer unit){
		this.unit = unit;
		return this;
	}
	
	public Information() {
	}

	public Integer evaluateInformation() {
		Integer romanNumeral = roman.convertRomanNumeralToNumber();
		if ( romanNumeral != null ) return ((Float)(romanNumeral * (float)credit / unit)).intValue();
		return null;
	}
	
	public String rawInformation() {
		return romanSymbol;
	}

	public void addInformation(Information information) {
		if ( RomanUtils.isRomanNumeral(information.rawInformation())){
			roman.addRomanSymbol(new RomanSymbolUnit(information.rawInformation()));
		}else{
			this.credit =  information.credit;
			this.unit = information.unit;
		}
	}
}