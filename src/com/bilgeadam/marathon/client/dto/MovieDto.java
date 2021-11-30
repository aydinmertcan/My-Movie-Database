package com.bilgeadam.marathon.client.dto;

import java.io.Serializable;

public class MovieDto implements Serializable{
	
	private static final long serialVersionUID = -1797596821423523926L;
	private String primaryTitle;
	private String startYear;
	private String genres;
	
	public MovieDto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "\t" + startYear + " - " + primaryTitle  + " - " + genres + "\n";
	}

	public String getPrimaryTitle() {
		return primaryTitle;
	}

	public void setPrimaryTitle(String primaryTitle) {
		this.primaryTitle = primaryTitle;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		if(startYear.equalsIgnoreCase("\\N"))
			this.startYear = "Not Defined";
		else
			this.startYear = startYear;
	}


	public String getGenres() {
		return genres;
	}

	public void setGenres(String string) {
		this.genres = string;
	}
	
	
}
