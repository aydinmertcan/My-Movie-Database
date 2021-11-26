package com.bilgeadam.marathon.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	    //Database bağlantısını kuracak class
	    private Connection connection;

	    //url username password çağırmak
	    private static DatabaseInformation databaseInformation;



	    //database bağlanmak için zorunlu yerdir.
	    private String url= databaseInformation.getUrl();
	    private String userName= databaseInformation.getUserName();
	    private String password= databaseInformation.getPassword();

	    //singleton design pattern 1. özellik
	    private static DatabaseConnection instance;

	    //singleton design pattern 2. özellik
	    private DatabaseConnection() {
	    try {
	        Class.forName(databaseInformation.getForNameData());
	        System.out.println("postgresql-42.3.1 jar Driver yüklendi");

	        this.connection= DriverManager.getConnection(url,userName,password);
	        System.out.println("Connection başarılı");
	    } catch (Exception e) {
	        System.out.println("Hatalar meydana geldi");
	        e.printStackTrace();
	    }
	    }
	    //static
	    static {
	        databaseInformation = new DatabaseInformation();
	        //nullPointer Exception
	    }

	    //singleton design pattern 3. özellik
	    public static DatabaseConnection getInstance() {
	        if(instance==null) {
	            instance = new DatabaseConnection();
	        } else
	            try {
	                if(instance.getConnection().isClosed()) {
	                    instance = new DatabaseConnection(); 
	                }
	            } catch (SQLException e) {

	                e.printStackTrace();
	            }
	        return instance;
	    }
	    //getter and setter
	    public Connection getConnection() {
	        return connection;
	    }
	    public static void main(String[] args) {
	        DatabaseConnection connection = new DatabaseConnection();
	    }

}
