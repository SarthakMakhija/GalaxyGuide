package org.tw.exercise.galaxy.kbase;

import java.util.ArrayList;
import java.util.List;

import org.tw.exercise.galaxy.exception.IncompleteConversationException;
import org.tw.exercise.galaxy.exception.InvalidRomanNumeralException;
import org.tw.exercise.galaxy.utils.RomanUtils;
import org.tw.exercise.galaxy.vo.Tokens;

public class ConversationProcessor {

	private List<String> conversation = new ArrayList<String>();
	
	private Parser parser = Parser.getInstance();
	
	private List<Tokens> queries = new ArrayList<Tokens>();
	
	private List<Response> responses = new ArrayList<>();
	
	public ConversationProcessor addConvesationStatement ( String statement ){
		conversation.add(statement);
		return this;
	}
	
	public List<Response> respondToConversationQueries () {
		addTokenToKnowledgeBase();
		evaluateQueries();
		return responses;
	}

	private void addTokenToKnowledgeBase(){
		for ( String input : conversation ){
			buildKnowledgeBase(parse(input));
		}
	}
	
	private Tokens parse ( String input ){
		return parser.parse(input);
	}
	
	private void buildKnowledgeBase( Tokens tokens ){
		if ( isQuery(tokens) ) 
			addQueriesToEvaluate(tokens);
		else 
			tokens.buildKnowledge(KnowledgeBase.getInstance());
	}
	
	private void addQueriesToEvaluate ( Tokens tokens ) {
		queries.add(tokens);
	}
	
	private void evaluateQueries(){
		for ( Tokens tokens : queries ){
			responses.add(buildQueryResponse(tokens));
		}
	}
	
	private Response buildQueryResponse( Tokens tokens ){
		try {
			return buildQueryResponseWithExceptionResponse(tokens);
		}catch ( InvalidRomanNumeralException | IncompleteConversationException e ){
			return new Response(tokens.asString(), e.getMessage());
		}
	}
	
	private Response buildQueryResponseWithExceptionResponse( Tokens tokens ) throws InvalidRomanNumeralException{
		Integer result = lookUpKnowledgeBasedForQueryEvaluation(tokens);
		
		if ( result == null ) return new Response(tokens.asString(), Response.UNKNOWN_QUERY_ANSWER);
		return new Response(tokens.asString(), RomanUtils.asString(result));
	}
	
	private Integer lookUpKnowledgeBasedForQueryEvaluation(Tokens tokens){
		return tokens.lookUpKnowledgeBasedForQueryEvaluation();
	}
	
	private boolean isQuery( Tokens tokens ){
		return tokens.isQuery();
	}
}