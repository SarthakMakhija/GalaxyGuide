package org.tw.exercise.galaxy.kbase;

import static org.tw.exercise.galaxy.utils.RomanUtils.CREDIT_IDENTIFIER;
import static org.tw.exercise.galaxy.utils.RomanUtils.isRomanNumeral;

import java.util.List;

public class InformationBuilder {

	public Information constructInformation(String token){
		if ( isInformationTypeCredit(token) )
			return constructInformationWithCredit(token);
		else if ( isRomanNumeral(token) )
			return constructRomanBasedInformation(token);
		else
			return null;
	}

	private Information constructRomanBasedInformation ( String token ){
		if ( isRomanNumeral(token) ) return new Information(token);
		return null;
	}
	
	private Information constructInformationWithCredit ( String token ){
		return new Information().withCredit((getCreditValue(token))).withUnit(getCreditUnits(token));
	}
	
	private boolean isInformationTypeCredit(String token){
		if ( token.toLowerCase().contains(CREDIT_IDENTIFIER) ) return true;
		return false;
	}
	
	private Integer getCreditUnits ( String token ){
		return Integer.valueOf(token.toLowerCase().replaceAll(CREDIT_IDENTIFIER, "").split(" ")[0]);
	}

	private Integer getCreditValue ( String token ){
		return Integer.valueOf(token.toLowerCase().replaceAll(CREDIT_IDENTIFIER, "").split(" ")[1]);
	}
	
	public Information buildExecutableInformation(List<Information> informations){
		return addToExecutableInformation(informations, initInformation());		
	}	

	private Information addToExecutableInformation(List<Information> informations, Information info){
		if ( !informations.isEmpty() ) {
			for ( Information information : informations ){
				info.addInformation(information);
			}
		}
		return info;
	}
	
	private Information initInformation(){
		return new Information();
	}
}