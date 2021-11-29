package com.bilgeadam.marathon.client.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.bilgeadam.marathon.utils.DatabaseConnection;
import com.bilgeadam.marathon.utils.ScriptRunner;

public class DistinctGenres {
	private String genre;
	private Set<String> distinctGenres;
	private HashMap<String, Set<String>> genreMovies;
	
	// Gives distinct genres set in the movies.csv ==> Film-Noir Action Adventure Horror War...
	// Romance Western Documentary Sci-Fi Drama Thriller (no genres listed) Crime Fantasy IMAX...
	// Animation Mystery Comedy Children Musical
	public Set<String> getDistinctGenres() {
		try (Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement()) {
			distinctGenres = new HashSet<String>();
			String path = "./src/com/bilgeadam/marathon/sqls/moviescsv/GenresQuery.sql"; 
			ResultSet rS =new ScriptRunner().executeQueries(stmt, path);
			
			while (rS.next()) {
				StringTokenizer token = new StringTokenizer(rS.getString(3), "|");
				while (token.hasMoreElements()) {
					String temp = token.nextElement().toString();
					distinctGenres.add(temp);
				}
			}
			System.out.println(distinctGenres);
		} catch (SQLException e) {
			e.getMessage();
		}
		return distinctGenres;
	}
	
	public void parseMovieTitle() {
		TreeMap<String, String> titleDates = new TreeMap<String, String>();
		addDateColumn("alter table movies add date varchar");
		String titleQuery = "select m.title from movies m";
		try(Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement()) {
			ResultSet rS =  stmt.executeQuery(titleQuery);

			
			
			while (rS.next()) {
				//Big Bully (1996) --> Big
				String fullyName = rS.getString(1);
				if(fullyName.length() > 8) {
					String title = fullyName.substring(0, (fullyName.length() - 7));
					if(title.endsWith(", The")) {
						title = title.substring(0, (title.length() - 5));
					} else if (title.endsWith(", A"))
						title = title.substring(0, (title.length() - 3));
					
					String date = fullyName.substring((fullyName.length() - 5), fullyName.length() - 1);
	//				movieTitle.add(rS.getString(1));
					updateDatesAndTitles(title, date, fullyName);
	//				System.out.println(title + date);
				} else {
					updateDatesAndTitles(fullyName, null, fullyName);
				}
//				
//				titleDates.put(title, date);
			}
			System.out.println("tmm");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void addDateColumn(String query1) {
		 
		try(Statement pStmt = DatabaseConnection.getInstance().getConnection().createStatement()) {
			pStmt.executeUpdate(query1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void updateDatesAndTitles(String title, String date, String fullyName) {
		String updateDatesAndTitles = "update movies set title = ?, date = ? where title like ?";
		try (PreparedStatement pStmt = DatabaseConnection.getInstance().getConnection().prepareStatement(updateDatesAndTitles)){
			
			pStmt.setString(1, title);
			pStmt.setString(2, date);
			pStmt.setString(3, fullyName);
			pStmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error occured while updating");
		}
		
	}

	public HashMap<String, Set<String>> addMovies2Genres (Set<String> genres) {
		String titleQuery = "select m.title from movies m where m.genres like ? limit 20";
		try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection().prepareStatement(titleQuery)){
			genreMovies = new HashMap<String, Set<String>>();
			
			for (String temp : genres) {
				System.out.println("\n" + temp + "\n--------------------------------------");
				stmt.setString(1, ("%"+temp+"%"));
//				stmt.setString(2, (temp+"%"));
				ResultSet rS = stmt.executeQuery();
//				ResultSet rS = stmt.executeQuery(String.format(titleQuery, temp));
				while(rS.next()) {
					String moviesWithGenres = rS.getString(1);
					System.out.println(moviesWithGenres);
				}	
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return genreMovies;
	}
	
	public static void main(String[] args) {
		DistinctGenres gen = new DistinctGenres();
		gen.addMovies2Genres(gen.getDistinctGenres());	
//		gen.parseMovieTitle();
	}
}
