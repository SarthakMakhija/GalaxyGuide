package org.tw.exercise.galaxy;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.tw.exercise.galaxy.exception.InvalidRomanNumeralException;
import org.tw.exercise.galaxy.kbase.ConversationProcessor;
import org.tw.exercise.galaxy.kbase.KnowledgeBase;
import org.tw.exercise.galaxy.kbase.Response;
import org.tw.exercise.galaxy.utils.RomanUtils;
import static org.tw.exercise.galaxy.utils.RomanUtils.CREDIT_IDENTIFIER;

public class ConversationProcessorTest {

	@After
	public void clearKnowledgeBase(){
		KnowledgeBase.getInstance().clear();
	}
	
	@Test
	public void respondToSimpleConversationWithoutQueryExpectValidResponse() {
		Assert.assertEquals(0, this.startConversation("prok is V").respondToConversationQueries().size());
	}

	@Test
	public void respondToSimpleConversationWithQueryExpectValidResponse_1() {
		Assert.assertEquals(new ArrayList<Response>() {
			{
				add(new Response("prok", "5"));
			}
		}, this.startConversation("prok is V", "what is prok ?").respondToConversationQueries());
	}

	@Test
	public void respondToSimpleConversationWithQueryExpectValidResponse_2() {
		Assert.assertEquals(new ArrayList<Response>() {
			{
				add(new Response("pish prok", "15"));
			}
		},	this.startConversation("prok is V", "pish is X", "what is pish prok ?").respondToConversationQueries());
	}

	@Test
	public void respondToComplexConversationWithQueryExpectValidResponse_1() {
		Assert.assertEquals(new ArrayList<Response>() {
			{
				add(new Response("pish tegj glob glob", "42"));
			}
		},	this.startConversation("glob is I", "prok is V", "pish is X", "tegj is L", "how much is pish tegj glob glob ?").respondToConversationQueries());
	}

	@Test
	public void respondToComplexConversationWithQueryExpectValidResponse_2() {
		Assert.assertEquals(new ArrayList<Response>() {
			{
				add(new Response("glob glob glob", "3"));
			}
		},	this.startConversation("glob is I", "how much is glob glob glob ?").respondToConversationQueries());
	}
	
	@Test
	public void respondToInvalidSimpleConversationWithQueryExpectValidResponse() {
		Assert.assertEquals(new ArrayList<Response>() {
			{
				add(new Response("wood woodchuck chuck woodchuck chuck wood", Response.UNKNOWN_QUERY_ANSWER));
			}
		},	this.startConversation("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?").respondToConversationQueries());
	}

	@Test
	public void respondToInvalidSimpleConversationWithQueryExpectInvalidRomanNumeralResponse() {
		Assert.assertEquals(new ArrayList<Response>() {
			{
				add(new Response("prok tegj", InvalidRomanNumeralException.INVALID_ROMAN));
			}
		},	this.startConversation("prok is I", "tegj is M", "what is prok tegj ?").respondToConversationQueries());
	}

	@Test
	public void respondToSimpleConversationWithCreditsQueryExpectValidResponse_1() {
		Assert.assertEquals(new ArrayList<Response>() {
			{
				add(new Response("glob prok silver", "68 " + RomanUtils.CREDIT_IDENTIFIER));
			}
		}, this.startConversation("glob is I", "prok is V", "glob glob silver is 34 Credits", "how many credits is glob prok silver ?").respondToConversationQueries());
	}

	@Test
	public void respondToSimpleConversationWithCreditsQueryExpectValidResponse_2() {
		Assert.assertEquals(new ArrayList<Response>() {
			{
				add(new Response("glob prok gold", "57800 " + CREDIT_IDENTIFIER));
			}
		}, this.startConversation("glob is I", "prok is V", "pish is X", "tegj is L", "glob prok Gold is 57800 Credits", "how many Credits is glob prok Gold?").respondToConversationQueries());
	}

	@Test
	public void respondToSimpleConversationWithCreditsQueryExpectValidResponse_3() {
		Assert.assertEquals(new ArrayList<Response>() {
			{
				add(new Response("glob prok iron", "782 " + CREDIT_IDENTIFIER));
			}
		}, this.startConversation("glob is I", "prok is V", "pish is X", "tegj is L", "pish pish Iron is 3910 Credits", "glob prok Gold is 57800 Credits", "how many CREDITS is glob prok Iron?").respondToConversationQueries());
	}

	private ConversationProcessor startConversation( String... conversations ) {
		ConversationProcessor conversationProcessor = new ConversationProcessor();
		if (conversations != null ){
			for ( String conversation : conversations ){
				conversationProcessor.addConvesationStatement(conversation);
			}
		}
		return conversationProcessor;
	}
}
