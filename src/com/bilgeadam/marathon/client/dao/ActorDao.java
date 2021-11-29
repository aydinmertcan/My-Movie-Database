package com.bilgeadam.marathon.client.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.bilgeadam.marathon.client.dto.ActorDto;

public class ActorDao {
	public void parseActorProperties(String property) {
		ActorDto actor = new ActorDto();
		String path = "/Users/mertcanaydin/Desktop/Marathon/Raw/TSV/names.tsv";
		readActorTsv(path);
		
	}
	
	
	public void readActorTsv (String path) {
		ArrayList<String> actorProperties = new ArrayList<String>();
		try(BufferedReader bR = new BufferedReader(new FileReader(path))) {
			String temp = null;
			String line = null;
			while ((line = bR.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer("\t");
				while (tokenizer.hasMoreElements()) {
					tokenizer.nextToken();
					temp = tokenizer.nextToken();
					if(temp === userInput)
					actorProperties.add(temp);
					temp = tokenizer.nextToken();
					
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
