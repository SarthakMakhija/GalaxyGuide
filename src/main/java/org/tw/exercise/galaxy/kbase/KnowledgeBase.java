package org.tw.exercise.galaxy.kbase;

import static org.tw.exercise.galaxy.utils.RomanUtils.asString;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KnowledgeBase {

	private static final KnowledgeBase instance = new KnowledgeBase();
	
	private static final Map<String, Information> INFORMATION_REGISTRY = new LinkedHashMap<>();

	private static final QueryProcessor queryProcessor = new QueryProcessor();
	
	private KnowledgeBase(){
		
	}

	public static KnowledgeBase getInstance(){
		return instance;
	}
	
	public void addToKnowledgeBase(List<String> tokens) {
		processTokens(tokens);
	}

	public Information lookUpInformationRegistry( String queryToken ){
		return INFORMATION_REGISTRY.get(queryToken);
	}
	
	private void processTokens( List<String> tokens ){
		if ( tokens.size() <= 2 ){
			addRomanBasedTokensToKnowledgeBase( tokens );
		}
		else{
			addCreditBasedTokensToKnowledgeBase(tokens);
		}
	}
	
	private void addRomanBasedTokensToKnowledgeBase(List<String> tokens) {
		for ( int count = 0; count < tokens.size() - 1; count++ ){
			addToKnowledgeBase(tokens.get(count), mapTokenToInformation(tokens.get(count + 1)));
		}
	}

	private Information mapTokenToInformation ( String token ){
		return getInformationFactory().constructInformation(token);
	}
	
	private void addCreditBasedTokensToKnowledgeBase ( List<String> tokens ) {
		for ( int count = 0; count < tokens.size() - 1; count++ ){
			if ( lookUpInformationRegistry(tokens.get(count)) == null ){
				Integer result = queryProcessor.evaluatePartialResult (tokens.subList(0, count)); 
				String tok = constructTokenWithUnitsCredits(result, tokens.subList(count+1, tokens.size()));
				addToKnowledgeBase(tokens.get(count), mapTokenToInformation(tok));
				break;
			}
		}
	}
	
	private String constructTokenWithUnitsCredits( Integer result, List<String> tokens ) {
		return asString(result) + " " + tokens.toString().replace("[", "").replace("]", "").replace(",", "");
	}

	private InformationBuilder getInformationFactory(){
		return new InformationBuilder();
	}
	
	private void addToKnowledgeBase ( String key, Information information ){
		if ( information != null ) INFORMATION_REGISTRY.put(key, information);
	}

	public void clear() {
		INFORMATION_REGISTRY.clear();
	}
}