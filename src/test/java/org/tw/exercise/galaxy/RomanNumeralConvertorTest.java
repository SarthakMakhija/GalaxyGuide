package org.tw.exercise.galaxy;

import org.junit.Assert;
import org.junit.Test;
import org.tw.exercise.galaxy.exception.InvalidRomanNumeralException;
import org.tw.exercise.galaxy.validator.RomanSymbolNoRepeatRuleValidator;
import org.tw.exercise.galaxy.validator.RomanSymbolRepeatCountRuleValidator;
import org.tw.exercise.galaxy.validator.RomanSymbolSingleSubtractionRuleValidator;
import org.tw.exercise.galaxy.validator.RomanSymbolSubtractionOnWeightRuleValidator;
import org.tw.exercise.galaxy.vo.RomanNumeral;
import org.tw.exercise.galaxy.vo.RomanSymbolUnit;

public class RomanNumeralConvertorTest {

	@Test
	public void convertEmptyRomanNumeralToNumberExpectNull() {
		Assert.assertNull(
				this.prepareRomanNumeral()
					.convertRomanNumeralToNumber());
	}


	@Test
	public void convertSimpleRomanNumeralToNumberExpectValidNumberConversion1() {
		Assert.assertEquals(
				2006,
				this.prepareRomanNumeral(RomanSymbolUnit.M, RomanSymbolUnit.M, RomanSymbolUnit.V, RomanSymbolUnit.I).convertRomanNumeralToNumber().intValue());
	}

	@Test
	public void convertComplexRomanNumeralToNumberExpectValidNumberConversion() {
		Assert.assertEquals(
				1944,
				this.prepareRomanNumeral(RomanSymbolUnit.M, RomanSymbolUnit.C, RomanSymbolUnit.M, RomanSymbolUnit.X, RomanSymbolUnit.L, RomanSymbolUnit.I, RomanSymbolUnit.V).convertRomanNumeralToNumber().intValue());
	}

	@Test
	public void convertNumberToRomanExpectValidConversion_1() {
		Assert.assertEquals("MCMXLIV", this.prepareRomanNumeral(1944).convertNumberToRoman());
	}

	@Test
	public void convertNumberToRomanExpectValidConversion_2() {
		Assert.assertEquals("MCMIII", this.prepareRomanNumeral(1903).convertNumberToRoman());
	}

	@Test(expected = InvalidRomanNumeralException.class)
	public void convertRomanNumeralToNumberWithValidationExpectFailedValidation() {
		this.prepareRomanNumeral(RomanSymbolUnit.D, RomanSymbolUnit.L, RomanSymbolUnit.V, RomanSymbolUnit.L).withValidator(new RomanSymbolNoRepeatRuleValidator()).convertRomanNumeralToNumber();
	}

	@Test
	public void convertRomanNumeralToNumberWithValidationExpectValidationSuccess() {
		Assert.assertEquals(
				550,
				this.prepareRomanNumeral(RomanSymbolUnit.D, RomanSymbolUnit.L).withValidator(new RomanSymbolNoRepeatRuleValidator()).convertRomanNumeralToNumber().intValue());
	}

	@Test
	public void convertRomanNumeralToNumberWithSymbolComparisonValidationExpectValidationSuccess_1() {
		Assert.assertEquals(
				5,
				this.prepareRomanNumeral(RomanSymbolUnit.I, RomanSymbolUnit.I, RomanSymbolUnit.V).withValidator(new RomanSymbolSingleSubtractionRuleValidator()).convertRomanNumeralToNumber().intValue());
	}

	@Test
	public void convertRomanNumeralToNumberWithSymbolComparisonValidationExpectValidationSuccess_2() {
		Assert.assertEquals(
				2000,
				this.prepareRomanNumeral(RomanSymbolUnit.M, RomanSymbolUnit.C, RomanSymbolUnit.M, RomanSymbolUnit.C).withValidator(new RomanSymbolSingleSubtractionRuleValidator()).convertRomanNumeralToNumber().intValue());
	}

	@Test
	public void convertRomanNumeralToNumberWithSymbolRepeatCountValidatorExpectValidationSuccess_1() {
		Assert.assertEquals(
				2000,
				this.prepareRomanNumeral(RomanSymbolUnit.M, RomanSymbolUnit.C, RomanSymbolUnit.M, RomanSymbolUnit.C).withValidator(new RomanSymbolRepeatCountRuleValidator()).convertRomanNumeralToNumber().intValue());
	}

	@Test
	public void convertRomanNumeralToNumberWithSymbolRepeatCountValidatorExpectValidationSuccess_2() {
		Assert.assertEquals(
				30,
				this.prepareRomanNumeral(RomanSymbolUnit.X, RomanSymbolUnit.X, RomanSymbolUnit.X).withValidator(new RomanSymbolRepeatCountRuleValidator()).convertRomanNumeralToNumber().intValue());
	}

	@Test
	public void convertRomanNumeralToNumberWithSymbolRepeatCountValidatorExpectValidationSuccess_3() {
		Assert.assertEquals(
				39,
				this.prepareRomanNumeral(RomanSymbolUnit.X, RomanSymbolUnit.X, RomanSymbolUnit.X, RomanSymbolUnit.I, RomanSymbolUnit.X).withValidator(new RomanSymbolRepeatCountRuleValidator()).convertRomanNumeralToNumber().intValue());
	}

	@Test(expected = InvalidRomanNumeralException.class)
	public void convertRomanNumeralToNumberWithSymbolRepeatCountValidatorExpectFailedValidation_1() {
		this.prepareRomanNumeral(RomanSymbolUnit.I, RomanSymbolUnit.I, RomanSymbolUnit.I, RomanSymbolUnit.I).withValidator(new RomanSymbolRepeatCountRuleValidator()).convertRomanNumeralToNumber();
	}

	@Test(expected = InvalidRomanNumeralException.class)
	public void convertRomanNumeralToNumberWithSymbolRepeatCountValidatorExpectFailedValidation_2() {
		this.prepareRomanNumeral(RomanSymbolUnit.X, RomanSymbolUnit.X, RomanSymbolUnit.X, RomanSymbolUnit.I, RomanSymbolUnit.X, RomanSymbolUnit.X)
				.withValidator(new RomanSymbolRepeatCountRuleValidator())
				.convertRomanNumeralToNumber();
	}

	@Test
	public void convertRomanNumeralToNumberWithSymbolSubtractionValidatorExpectValidatioSuccess_1() {
		Assert.assertEquals(
				1500,
				this.prepareRomanNumeral(RomanSymbolUnit.M, RomanSymbolUnit.D).withValidator(new RomanSymbolSubtractionOnWeightRuleValidator()).convertRomanNumeralToNumber().intValue());
	}

	@Test
	public void convertRomanNumeralToNumberWithSymbolSubtractionValidatorExpectValidatioSuccess_2() {
		Assert.assertEquals(
				1944,
				this.prepareRomanNumeral(RomanSymbolUnit.M, RomanSymbolUnit.C, RomanSymbolUnit.M, RomanSymbolUnit.X, RomanSymbolUnit.L, RomanSymbolUnit.I, RomanSymbolUnit.V)
						.withValidator(new RomanSymbolSubtractionOnWeightRuleValidator())
						.convertRomanNumeralToNumber().intValue());
	}

	@Test(expected = InvalidRomanNumeralException.class)
	public void convertRomanNumeralToNumberWithSymbolSubtractionValidatorExpectFailedValidatio() {
		this.prepareRomanNumeral(RomanSymbolUnit.I, RomanSymbolUnit.M)
				.withValidator(new RomanSymbolSubtractionOnWeightRuleValidator())
				.convertRomanNumeralToNumber();
	}

	@Test
	public void convertRomanNumeralToNumberWithAllRulesExpectValidationSuccess_1() {
		Assert.assertEquals(
				2006,
				this.prepareRomanNumeralWithAllRules(RomanSymbolUnit.M, RomanSymbolUnit.M, RomanSymbolUnit.V, RomanSymbolUnit.I).convertRomanNumeralToNumber().intValue());
	}

	@Test
	public void convertRomanNumeralToNumberWithAllRulesExpectValidationSuccess_2() {
		Assert.assertEquals(
				1944,
				this.prepareRomanNumeralWithAllRules(RomanSymbolUnit.M, RomanSymbolUnit.C, RomanSymbolUnit.M, RomanSymbolUnit.X, RomanSymbolUnit.L, RomanSymbolUnit.I, RomanSymbolUnit.V)
						.convertRomanNumeralToNumber().intValue());
	}

	@Test(expected = InvalidRomanNumeralException.class)
	public void convertRomanNumeralToNumberWithAllRulesExpectValidationFailed_1() {
		this.prepareRomanNumeralWithAllRules(RomanSymbolUnit.X, RomanSymbolUnit.X, RomanSymbolUnit.X, RomanSymbolUnit.I, RomanSymbolUnit.X, RomanSymbolUnit.X)
				.convertRomanNumeralToNumber();
	}

	@Test(expected = InvalidRomanNumeralException.class)
	public void convertRomanNumeralToNumberWithAllRulesExpectValidationFailed_2() {
		this.prepareRomanNumeralWithAllRules(RomanSymbolUnit.X, RomanSymbolUnit.X, RomanSymbolUnit.I, RomanSymbolUnit.M)
				.convertRomanNumeralToNumber();
	}

	@Test(expected = InvalidRomanNumeralException.class)
	public void convertRomanNumeralToNumberWithAllRulesExpectValidationFailed_3() {
		this.prepareRomanNumeralWithAllRules(RomanSymbolUnit.X, RomanSymbolUnit.X, RomanSymbolUnit.X, RomanSymbolUnit.D, RomanSymbolUnit.D)
				.convertRomanNumeralToNumber();
	}

	@Test(expected = InvalidRomanNumeralException.class)
	public void convertRomanNumeralToNumberWithAllRulesExpectValidationFailed_4() {
		this.prepareRomanNumeralWithAllRules(RomanSymbolUnit.I, RomanSymbolUnit.V, RomanSymbolUnit.V)
				.convertRomanNumeralToNumber();
	}
	
	@Test
	public void convertRomanNumeralToNumberWithAllRulesExpectValidationSuccess_3() {
		Assert.assertEquals(
				42,
				this.prepareRomanNumeralWithAllRules(RomanSymbolUnit.X, RomanSymbolUnit.L, RomanSymbolUnit.I, RomanSymbolUnit.I)
						.convertRomanNumeralToNumber().intValue());
	}

	private RomanNumeral prepareRomanNumeral( RomanSymbolUnit... symbols ) {
		RomanNumeral roman = initRomanNumeral();
		addRomanSymbols(roman, symbols);
		return roman;
	}

	private RomanNumeral prepareRomanNumeral(int decimalEquivalent) {
		return new RomanNumeral(decimalEquivalent);
	}

	private RomanNumeral initRomanNumeral(){
		return new RomanNumeral();
	}
	
	private void addRomanSymbols ( RomanNumeral roman, RomanSymbolUnit... symbols ){
		if ( symbols != null ){
			for ( RomanSymbolUnit u : symbols ){
				roman.addRomanSymbol(u);
			}
		}
	}
	
	private RomanNumeral prepareRomanNumeralWithAllRules( RomanSymbolUnit... symbols ) {
		RomanNumeral roman = initRomanNumeral();
		addRomanSymbols(roman, symbols);
		return roman
				.withValidator(new RomanSymbolNoRepeatRuleValidator())
				.withValidator(new RomanSymbolRepeatCountRuleValidator())
				.withValidator(new RomanSymbolSingleSubtractionRuleValidator())
				.withValidator(new RomanSymbolSubtractionOnWeightRuleValidator());
	}
}