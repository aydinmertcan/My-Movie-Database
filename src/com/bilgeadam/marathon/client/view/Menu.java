package com.bilgeadam.marathon.client.view;

import com.bilgeadam.marathon.client.dao.ClientDao;
import com.bilgeadam.marathon.client.dao.MovieDao;
import com.bilgeadam.marathon.client.dto.ActorDto;
import com.bilgeadam.marathon.utils.McUtils;

public class Menu {
	public void showMenu() {
		menuItems();
	}

	private void menuItems() {
		MovieDao movieDao = new MovieDao();
		boolean isCreated = false;
		int choice = 0;
		McUtils.header("Welcome to MyMDB");
		System.out.println();
		do {
		choice = McUtils.readInt("Please enter your choice:\n" + "\t1) Create Database\n" + "\t2) Delete Database\n"
				+ "\t3) Make a Query\n" + "\t99) Exit\n");

		
			switch (choice) {
			case 1:
				isCreated = movieDao.createCsvTables();
				break;
			case 2:
				movieDao.deleteDatabase();
				break;
			case 3:
				if(isCreated) {
					showQueryMenu();
				} else {
					System.out.println("Please create database!!");
				}
				break;
				
			case 99:
				System.exit(-1);
				break;
			}
		
		} while(true);

	}

	private void showQueryMenu() {
		MovieDao movieDao = new MovieDao();
		System.out.println();
		int choice = 0;
		do {
			choice = McUtils.readInt("Please enter your choice:\n" + "\t1) Show movies of genres\n"
					+ "\t2) Show movies in the selected year\n" + "\t3) Show selected actor movies\n" + "\t99) Exit\n");
			switch (choice) {
			case 1:
				movieDao.addMovies2Genres(movieDao.getDistinctGenres());
				break;
			case 2:
				movieDao.showSelectedYearMovies(movieDao.getDistinctGenres());
				break;
			case 3:
				String actorName = McUtils.readString("Please enter actor name.");
				ActorDto actor = ClientDao.getInstance().requestActorName(actorName);
				if(actor != null) {
					System.out.println(actor.toString());
				} else {
					System.err.println("Actor cannot be found.");
				}
				
				
			case 99:
				System.exit(-1);
				break;
			
			}

		} while(true);
		
	}
}
