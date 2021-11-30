package com.bilgeadam.marathon.client.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.bilgeadam.marathon.client.dto.ActorDto;
import com.bilgeadam.marathon.client.dto.MovieDto;
import com.bilgeadam.marathon.resources.Resources;

public class ActorDao implements Serializable {
	 private static final long serialVersionUID = 1766167175450194417L;
	    
	    public ActorDto read(ActorDto dto) {
	        String str;
	        ActorDto actorDto = null;
	        String path = Resources.getInstance().getPath("namesTSV");
	        try (BufferedReader bR = new BufferedReader(new FileReader(new File(path)))) {
	            while ((str = bR.readLine()) != null) {
	                if (str.contains(dto.getPrimaryName())) {
	                    actorDto = new ActorDto();
	                    StringTokenizer tokenizer = new StringTokenizer(str, "\t");
	                    tokenizer.nextToken();
	                    if (tokenizer.hasMoreTokens())
	                        actorDto.setPrimaryName(tokenizer.nextToken());
	                    if (tokenizer.hasMoreTokens())
	                        actorDto.setBirthYear(tokenizer.nextToken());
	                    if (tokenizer.hasMoreTokens())
	                        actorDto.setDeathYear(tokenizer.nextToken());
	                    if (tokenizer.hasMoreTokens())
	                        actorDto.setPrimaryProfession(tokenizer.nextToken());
	                    if (tokenizer.hasMoreTokens())
	                        actorDto.setKnownForTitles(tokenizer.nextToken());
	                    actorDto = findActorMovies(actorDto);
	                    System.out.println(actorDto);
	                    break;
	                }
	            }
	            if (actorDto == null) {
	                System.out.println("Actor cannot be found.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return actorDto;
	    }
	    
	    public ActorDto findActorMovies(ActorDto actorDto) {
	        
	        List<String> imdbIds = new ArrayList<>();
	        HashMap<Integer ,MovieDto> movieDtos = new HashMap<Integer, MovieDto>();
	        String path = Resources.getInstance().getPath("moviesTSV");
	        StringTokenizer st = new StringTokenizer(actorDto.getKnownForTitles(), ",");
	        while (st.hasMoreElements()) {
	            imdbIds.add(st.nextToken());
	        }
	        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
	            String line;
	            int i = 1;
	            while ((line = br.readLine()) != null) {
	                for (String imdbId : imdbIds) {
	                    if (line.startsWith(imdbId)) {
	                        MovieDto movieDto = new MovieDto();
	                        StringTokenizer tokenizer = new StringTokenizer(line, "\t");
	                        while (tokenizer.hasMoreTokens()) {
	                            tokenizer.nextToken();
	                            tokenizer.nextToken();
	                            movieDto.setPrimaryTitle(tokenizer.nextToken());
	                            tokenizer.nextToken();
	                            tokenizer.nextToken();
	                            movieDto.setStartYear(tokenizer.nextToken());
	                            tokenizer.nextToken();
	                            tokenizer.nextToken();
	                            movieDto.setGenres(tokenizer.nextToken());
	                        }
	                        movieDtos.put(i, movieDto);
	                        i++;
	                    }
	                }
	                
	            }
	            actorDto.setMoviesOfActor(movieDtos);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return actorDto;
	    }
	    
//	    public void showActorDetails() {
//	    	ActorDto actorDto = new ActorDto();
//	    	HashMap<Integer ,MovieDto> movies = actorDto.getMoviesOfActor();
//	    	int i = 0;
//	    	StringBuilder strBuilder = new StringBuilder();
//	    	for (MovieDto movieDto : movies) {
//				movieDto.getPrimaryTitle();
//			}
//	    }
	   
	    
	    public static void main(String[] args) {
	        ActorDto dto = new ActorDto();
	        dto.setPrimaryName("Gerard Butler");
	        ActorDao dao = new ActorDao();
	        dao.read(dto);
	    }
}
