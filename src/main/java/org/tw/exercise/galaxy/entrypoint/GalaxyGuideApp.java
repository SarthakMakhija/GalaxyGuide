package org.tw.exercise.galaxy.entrypoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.tw.exercise.galaxy.kbase.ConversationProcessor;
import org.tw.exercise.galaxy.kbase.Response;


public class GalaxyGuideApp {

	private static final String NO_CONVERSATION = "			No conversation took place. Ending the program, Bye Bye";
	
	public static void main ( String[] args ){
		presentHeaderToUser();
		List<String> inputs = processFileBasedConversations();

		if ( isConversationEmpty(inputs) ) displayMessageToUser(NO_CONVERSATION);
		else {
			displayResponsesToUser(processConversation(inputs));
		}
	}
	
	private static List<String> processFileBasedConversations() {
		List<String> inputs = null;
		try {
			inputs = readConversationsFromFile(acceptUserInput());
		} catch (IOException e) {
			displayMessageToUser(e.getMessage());
		}
		return inputs;
	}

	private static List<String> readConversationsFromFile(String userInput) throws IOException{
		return readFile ( userInput );
	}

	private static void presentHeaderToUser(){
		System.out.println("------------------------------------------------------------------------------------------------------------");
		System.out.println("					Galaxy Guide			");
		System.out.println("			Enter path to the file on LOCAL MACHINE (containing conversation)");
		System.out.println("------------------------------------------------------------------------------------------------------------");
	}
	
	private static void displayResponsesToUser ( List<Response> responses ){
		for ( Response r : responses ){
			System.out.println(r.getQuestion() + " : " + r.getAnswer());
		}
	}
	
	private static List<Response> processConversation( List<String> inputs ){
		return startConversation( inputs ).respondToConversationQueries();
	}
	
	private static ConversationProcessor startConversation( List<String> inputs ){
		ConversationProcessor conversationProcessor = new ConversationProcessor();
		for ( String str : inputs ){
			conversationProcessor.addConvesationStatement(str);
		}
		return conversationProcessor;
	}
	
	private static void displayMessageToUser( String msg ){
		System.out.println(msg);
	}
	
	private static boolean isConversationEmpty(List<String> inputs){
		return inputs == null || inputs.isEmpty();
	}
	
	private static String acceptUserInput() {
		Scanner sc = constructScanner();
		String filePath = sc.next();
		closeScanner(sc);
		return filePath;
	}
	
	private static void closeScanner( Scanner sc ) {
		sc.close();
	}
	
	private static List<String> readFile( String filePath ) throws IOException{
		File fl = new File(filePath);
		if ( !fl.exists() ) throw new FileNotFoundException(filePath + " does not exist ");
		else{
			return collectConversation(fl);
		}
	}
	
	private static List<String> collectConversation( File fl) throws IOException{
		List<String> lines = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(fl));){
			String line = br.readLine();
			while ( line != null ){
				lines.add(line);
				line = br.readLine();
			}
		}
		finally{
			
		}
		return lines;
	}
	
	private static Scanner constructScanner(){
		return new Scanner(System.in); //.useDelimiter(System.getProperty("line.separator"));
	}
}