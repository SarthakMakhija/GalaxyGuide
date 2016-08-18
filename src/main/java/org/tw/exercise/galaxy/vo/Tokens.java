package org.tw.exercise.galaxy.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tw.exercise.galaxy.kbase.KnowledgeBase;
import org.tw.exercise.galaxy.kbase.QueryProcessor;

public class Tokens {

	private List<String> 				tokens 					= new ArrayList<>();

	private Set<String>					ignoreableTokens 		= new HashSet<>();
	
	private static final String[] 		DFAULT_IGNOREABLE_TOKENS = new String[]{"is", "what", "why", "how", "the", "could", "then", "if", "much", "many", "you", "a", "are", "where"};
	
	private static final String QUERY_IDENTIFIER = "?";
	
	private static final QueryProcessor queryProcessor = new QueryProcessor();
	
	public Tokens(){
		addDefaultIgnoreableTokens();
	}
	
	public Tokens addTokenUnit ( String tokenUnit ){
		addToTokensIfNotIgnoreable(tokenUnit);
		return this;
	}
	
	public Tokens addIgnoreableToken ( String tokenUnit ){
		addToIgnoreableTokens(tokenUnit);
		return this;
	}
	
	private void addToTokensIfNotIgnoreable ( String tokenUnit ){
		if ( !isIgnoreableToken( tokenUnit ) ) tokens.add(tokenUnit.toLowerCase());
	}
	
	private void addDefaultIgnoreableTokens(){
		for ( String tokenUnit : DFAULT_IGNOREABLE_TOKENS ){
			addToIgnoreableTokens(tokenUnit);
		}
	}
	
	private void addToIgnoreableTokens( String tokenUnit ){
		ignoreableTokens.add(tokenUnit.toLowerCase());
	}
	
	private boolean isIgnoreableToken  ( String tokenUnit ){
		return ignoreableTokens.contains(tokenUnit.toLowerCase());
	}

	public void buildKnowledge(KnowledgeBase knowledgeBase) {
		addTokensToKnowledgeBase(knowledgeBase);
	}

	public Integer lookUpKnowledgeBasedForQueryEvaluation() {
		removeQueryIdentifier();
		return queryProcessor.evaluateTokens(tokens);
	}
	
	private void removeQueryIdentifier(){
		tokens.remove(QUERY_IDENTIFIER);
		relaceQueryIdentifierInToken();
	}
	
	public boolean isQuery(){
		return tokens.contains(QUERY_IDENTIFIER) || isQueryIdentifierEmbeddedinToken();
	}
	
	private void relaceQueryIdentifierInToken(){
		for ( String token : tokens ){
			tokens.set(tokens.indexOf(token), token = token.replace(QUERY_IDENTIFIER, "")); 
		}
	}
	
	private boolean isQueryIdentifierEmbeddedinToken(){
		for ( String token : tokens ){
			if ( token.contains(QUERY_IDENTIFIER) ) return true;
		}
		return false;
	}

	public String asString(){
		return Arrays.toString(tokens.toArray(new String[tokens.size()])).replace("[", "").replace("]", "").replace(",", "");
	}
	
	private void addTokensToKnowledgeBase( KnowledgeBase knowledgeBase ){
		knowledgeBase.addToKnowledgeBase(tokens);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tokens other = (Tokens) obj;
		if (tokens == null) {
			if (other.tokens != null)
				return false;
		} else if (!tokens.equals(other.tokens))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Token [tokens=" + tokens + "]";
	}
}