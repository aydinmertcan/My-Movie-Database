package com.bilgeadam.marathon.client.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.bilgeadam.marathon.utils.DatabaseConnection;
import com.bilgeadam.marathon.utils.McUtils;
import com.bilgeadam.marathon.utils.ScriptRunner;

public class MovieDao {
	public static boolean isParsed;
	public static boolean isColumnAdded;

	public boolean createCsvTables() {

		boolean isExecuted = false;
		
			try (Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement()) {
				String path = "./src/com/bilgeadam/marathon/sqls/moviescsv/CreateCsvTables.sql";
				isExecuted = new ScriptRunner().executeCreationStatement(stmt, path);
				if (isExecuted) {
					System.out.println("Tables are successfully created.");
					parseMovieTitle();
				} else
					System.err.println("Error occured while creating tables.");

			} catch (SQLException e) {
		}
		
		return isExecuted;
	}

	// Gives distinct genres set in the movies.csv ==> Film-Noir Action Adventure
	// Horror War...
	// Romance Western Documentary Sci-Fi Drama Thriller (no genres listed) Crime
	// Fantasy IMAX...
	// Animation Mystery Comedy Children Musical
	public Set<String> getDistinctGenres() {
		Set<String> distinctGenres = null;
		try (Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement()) {
			distinctGenres = new HashSet<String>();
			String path = "./src/com/bilgeadam/marathon/sqls/moviescsv/GenresQuery.sql";
			ResultSet rS = new ScriptRunner().executeQueries(stmt, path);

			while (rS.next()) {
				StringTokenizer token = new StringTokenizer(rS.getString(3), "|");
				while (token.hasMoreElements()) {
					String temp = token.nextElement().toString();
					distinctGenres.add(temp);
				}
			}
//			System.out.println(distinctGenres);
		} catch (SQLException e) {
			e.getMessage();
		}
		return distinctGenres;
	}

	public boolean parseMovieTitle() {
		if (!isColumnAdded)
			addDateColumn("alter table movies add date varchar");
		String titleQuery = "select m.title from movies m";
		try (Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement()) {
			ResultSet rS = stmt.executeQuery(titleQuery);

			while (rS.next()) {
				// Big Bully (1996) --> Big
				String fullyName = rS.getString(1);
				if (fullyName.length() > 8) {
					String title = fullyName.substring(0, (fullyName.length() - 7));
					if (title.endsWith(", The")) {
						title = title.substring(0, (title.length() - 5));
					} else if (title.endsWith(", A"))
						title = title.substring(0, (title.length() - 3));

					String date = fullyName.substring((fullyName.length() - 5), fullyName.length() - 1);
					updateDatesAndTitles(title, date, fullyName);
				} else {
					updateDatesAndTitles(fullyName, null, fullyName);
				}
			}
			isParsed = true;
			System.out.println("Succesfully parsed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isParsed;
	}

	private void addDateColumn(String query1) {
		try (Statement pStmt = DatabaseConnection.getInstance().getConnection().createStatement()) {
			pStmt.executeUpdate(query1);
			isColumnAdded = true;
		} catch (SQLException e) {
			System.err.println(
					"Error occured while adding date column to movies table at Postgresql(MovieDao.addDateColumn)");
			;
		}
	}

	private void updateDatesAndTitles(String title, String date, String fullyName) {
		String updateDatesAndTitles = "update movies set title = ?, date = ? where title like ?";
		try (PreparedStatement pStmt = DatabaseConnection.getInstance().getConnection()
				.prepareStatement(updateDatesAndTitles)) {

			pStmt.setString(1, title);
			pStmt.setString(2, date);
			pStmt.setString(3, fullyName);
			pStmt.executeUpdate();
		} catch (Exception e) {
			System.err.println("Error occured while updating");
		}

	}

	public void addMovies2Genres(Set<String> genres) {
		if (!isParsed)
			parseMovieTitle();
		else {
			String titleQuery = "select m.title from movies m where m.genres like ? limit 10";
			try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection()
					.prepareStatement(titleQuery)) {
				for (String temp : genres) {
					System.out.println("\n" + temp + "\n--------------------------------------");
					stmt.setString(1, ("%" + temp + "%"));
					ResultSet rS = stmt.executeQuery();
					while (rS.next()) {
						String moviesWithGenre = rS.getString(1);
						System.out.println(moviesWithGenre);
					}
				}
			} catch (Exception e) {
				System.err.println("Error occured in addMovies2Genres");
			}
		}
	}

	public void showSelectedYearMovies(Set<String> genres) {
		String year = McUtils.readString("Please enter the year you want to see");
		if (!isParsed) {
			parseMovieTitle();
		} else {
			String titleQuery = "select m.title from movies m where m.genres like ? and m.date like ? limit 10";
			try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection()
					.prepareStatement(titleQuery)) {
				System.out.println(year);
				for (String temp : genres) {
					System.out.println("\n" + temp + "\n--------------------------------------");
					stmt.setString(1, ("%" + temp + "%"));
					stmt.setString(2, year);
					ResultSet rS = stmt.executeQuery();
					while (rS.next()) {
						String moviesWithGenre = rS.getString(1);
						System.out.println(moviesWithGenre);
					}
				}
			} catch (Exception e) {
				System.err.println("Error occured in addMovies2Genres");
			}
		}
	}

	public void deleteDatabase() {
		boolean isExecuted = false;
		try (Statement stmt = DatabaseConnection.getInstance().getConnection().createStatement()) {
			String path = "./src/com/bilgeadam/marathon/sqls/moviescsv/DeleteDatabase.sql";
			isExecuted = new ScriptRunner().executeCreationStatement(stmt, path);
			if (isExecuted) {
				System.out.println("Tables are successfully deleted.");
			} else
				System.err.println("Error occured while deleting tables.");

		} catch (SQLException e) {
		}
	}



	public static void main(String[] args) {
		MovieDao movieDao = new MovieDao();
//		movieDao.createCsvTables();
//		movieDao.addMovies2Genres(movieDao.getDistinctGenres());
		movieDao.showSelectedYearMovies(movieDao.getDistinctGenres());

	}
}
