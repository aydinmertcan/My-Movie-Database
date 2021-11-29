package com.bilgeadam.marathon.client.dto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ActorDto {
	private String primaryName;
	private String birthYear;
	private String deathYear;
	private ArrayList<String> knownForTitles;
	
	public ActorDto() {
		
	}
	
	
	public ActorDto(String primaryName, String birthYear, String deathYear, ArrayList<String> knownForTitles) {
		super();
		this.primaryName = primaryName;
		this.birthYear = birthYear;
		this.deathYear = deathYear;
		this.knownForTitles = knownForTitles;
	}

	
	@Override
	public String toString() {
		return "ActorDto [primaryName=" + primaryName + ", birthYear=" + birthYear + ", deathYear=" + deathYear
				+ ", knownForTitles=" + knownForTitles + "]";
	}




	public String getPrimaryName() {
		return primaryName;
	}


	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}


	public String getBirthYear() {
		return birthYear;
	}


	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}


	public String getDeathYear() {
		return deathYear;
	}


	public void setDeathYear(String deathYear) {
		this.deathYear = deathYear;
	}


	public ArrayList<String> getKnownForTitles() {
		return knownForTitles;
	}


	public void setKnownForTitles(ArrayList<String> knownForTitles) {
		this.knownForTitles = knownForTitles;
	}
	
	
	
	
}



