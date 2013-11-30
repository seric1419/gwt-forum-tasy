package com.project.gwtforum.server.database;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.project.gwtforum.shared.Constants;

public class Database {

	private ConnectionSource connectionSource;
	
	private static Database instance;
	
	private Database(){
		try {
			connectionSource = new JdbcConnectionSource(Constants.DATABASE_URL);
		} catch (SQLException e) {
			
		}
	}
	
	public static Database getInstance() {
		if (instance == null){
			instance = new Database();
		}
		return instance;
	}
}
