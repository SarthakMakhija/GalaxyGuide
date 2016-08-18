package org.tw.exercise.galaxy.kbase;

import java.util.ArrayList;
import java.util.List;

import org.tw.exercise.galaxy.exception.InvalidRomanNumeralException;

public class QueryProcessor {

	public Integer evaluateTokens( List<String> tokens ) throws InvalidRomanNumeralException{
		Information info =  collectInformationFromTokens(tokens);
		if ( info != null ) return info.evaluateInformation();
		
		return null;
	}

	private Information collectInformationFromTokens ( List<String> tokens ) {
		List<Information> inferences = convertTokensToProcessedInformation(tokens);
		return getInformationFactory().buildExecutableInformation(inferences);
	}

	private List<Information> convertTokensToProcessedInformation( List<String> tokens ){
		List<Information> informations = new ArrayList<>();
		for ( String token : tokens ) 	addToInformation ( KnowledgeBase.getInstance().lookUpInformationRegistry(token), informations );

		return informations;
	}
	
	private void addToInformation ( Information information, List<Information> informations ){
		if ( information != null ) informations.add(information);
	}

	private InformationBuilder getInformationFactory(){
		return new InformationBuilder();
	}
	
	public Integer evaluatePartialResult ( List<String> subTokens ){
		return evaluateTokens(subTokens);
	}
}