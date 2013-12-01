package com.project.gwtforum.server.database;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.project.gwtforum.shared.Constants;

public class Database {

	private ConnectionSource connectionSource;
	
	private Dao<User, Integer> usersDao;
	
	private static Database instance;
	
	private Database(){
		try {
			connectionSource = new JdbcConnectionSource(Constants.DATABASE_URL);
			setupDatabase();
		} catch (SQLException e) {
			
		}
	}
	
	public static Database getInstance() {
		if (instance == null){
			instance = new Database();
		}
		return instance;
	}
	
	private void setupDatabase() throws SQLException{
		usersDao = DaoManager.createDao(connectionSource, User.class);
		
		TableUtils.createTable(connectionSource, User.class);
	}
	
	public Dao<User, Integer> getUsersDao() {
		return this.usersDao;
	}
}
