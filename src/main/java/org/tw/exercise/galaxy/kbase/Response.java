package org.tw.exercise.galaxy.kbase;


import static org.tw.exercise.galaxy.utils.RomanUtils.CREDIT_IDENTIFIER;

public class Response {

	private String question;
	
	private String answer;
	
	public static final String UNKNOWN_QUERY_ANSWER = "I have no idea about this";

	public Response(String question, String answer) {
		super();
		new ResponseFormatter().setFormattedResponse(question, answer);
	}
	
	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Response other = (Response) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Response [question=" + question + ", answer=" + answer + "]";
	}
	
	private class ResponseFormatter {
		
		public void setFormattedResponse ( String question, String answer ){
			if ( question.toLowerCase().contains(CREDIT_IDENTIFIER) ){
				Response.this.question = question.toLowerCase().replace(CREDIT_IDENTIFIER, "").trim();
				Response.this.answer = answer + " " + CREDIT_IDENTIFIER;
			}
			else{
				Response.this.question = question;
				Response.this.answer = answer;
			}
		}
	}
}