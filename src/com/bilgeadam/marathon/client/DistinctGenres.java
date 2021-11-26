package com.bilgeadam.marathon.client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

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
		} catch (SQLException e) {
			e.getMessage();
		}
		return distinctGenres;
	}
	
	public HashMap<String, Set<String>> addMovies2Genres (Set<String> genres) {
		String titleQuery = "select m.title from movies m where m.genres like ? limit 10";
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
//				System.out.println(rs.get);
				
//				stmt.setString(1, temp);
//				ResultSet rS = stmt.executeQuery();
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return genreMovies;
		
	}
	
	public static void main(String[] args) {
		DistinctGenres gen = new DistinctGenres();
		gen.addMovies2Genres(gen.getDistinctGenres());		
	}
}
