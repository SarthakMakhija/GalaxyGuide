package org.tw.exercise.galaxy.exception;

public class IncompleteConversationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public static final String INCOMPLETE_CONVERSATION = "Incomplete Conversation";
	
	public IncompleteConversationException(String message){
		super(message);
	}
	
	public IncompleteConversationException(String message, Throwable cause){
		super(message, cause);
	}

}
