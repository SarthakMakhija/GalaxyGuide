package org.tw.exercise.galaxy.kbase;

import static org.tw.exercise.galaxy.utils.RomanUtils.split;

import org.tw.exercise.galaxy.vo.Tokens;

public class Parser {

	private static final String DFAULT_SPLIT_STRING = " ";
	
	private static final Parser parser = new Parser();
	
	private String separator = DFAULT_SPLIT_STRING;
	
	private Parser(){
		
	}
	
	public static Parser getInstance(){
		return parser;
	}
	
	public void setSplitString ( String separator ){
		this.separator = separator;
	}
	
	public Tokens parse ( String input ){
		return breakInputIntoTokens(input);
	}
	
	private Tokens breakInputIntoTokens ( String input ){
		Tokens token = constructToken();
		for ( String e : split(input, separator) ){
			token.addTokenUnit(e);
		}
		return token;
	}
	
	private Tokens constructToken () {
		return new Tokens();
	}
}