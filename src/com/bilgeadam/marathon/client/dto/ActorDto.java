package com.bilgeadam.marathon.client.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ActorDto implements Serializable{
	private static final long serialVersionUID = -2801976192211295685L;
	private String primaryName;
	private String birthYear;
	private String deathYear;
	private String primaryProfession;
	private String knownForTitles;
	private HashMap<Integer, MovieDto> moviesOfActor;

	public ActorDto() {

	}

	public ActorDto(String primaryName, String birthYear, String deathYear, String knownForTitles) {
		super();
		this.primaryName = primaryName;
		this.birthYear = birthYear;
		this.deathYear = deathYear;
		this.knownForTitles = knownForTitles;
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		for (Map.Entry<Integer, MovieDto> temp : moviesOfActor.entrySet()) {
			Integer key = temp.getKey();
			MovieDto val = temp.getValue();	
			strBuilder.append("\t").append(key).append(" - ").append(val.getStartYear())
			.append(" - ").append(val.getPrimaryTitle()).append(" - ")
			.append(val.getGenres()).append("\n");
		}
		return "Name: " + primaryName + "\n(" + birthYear + " - " + deathYear + ")\nProfessions: " + primaryProfession
				+ "\n--------------------\nMovies\n" +strBuilder.toString();
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
		if (deathYear.equalsIgnoreCase("\\N"))
			this.deathYear = "Alive";
		else
			this.deathYear = deathYear;
	}

	public String getKnownForTitles() {
		return knownForTitles;
	}

	public void setKnownForTitles(String knownForTitles) {
		this.knownForTitles = knownForTitles;
	}

	public String getPrimaryProfession() {
		return primaryProfession;
	}

	public void setPrimaryProfession(String primaryProfession) {
		this.primaryProfession = primaryProfession;
	}

	public HashMap<Integer, MovieDto> getMoviesOfActor() {
		return moviesOfActor;
	}

	public void setMoviesOfActor(HashMap<Integer, MovieDto> moviesOfActor) {
		this.moviesOfActor = moviesOfActor;
	}
	
	

}
