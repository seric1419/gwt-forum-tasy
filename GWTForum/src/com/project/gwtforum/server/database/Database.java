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
	
	private Dao<Category, Integer> categoriesDao;
	
	private Dao<Forum, Integer> forumsDao;
	
	private Dao<Thread, Integer> threadsDao;
	
	private Dao<Reply, Integer> repliesDao;
	
	private static Database instance = new Database();
	
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
		TableUtils.createTableIfNotExists(connectionSource, User.class);
		
		categoriesDao = DaoManager.createDao(connectionSource, Category.class);
		TableUtils.createTable(connectionSource, Category.class);
		
		forumsDao = DaoManager.createDao(connectionSource, Forum.class);
		TableUtils.createTable(connectionSource, Forum.class);
		
		threadsDao = DaoManager.createDao(connectionSource, Thread.class);
		TableUtils.createTable(connectionSource, Thread.class);
		
		repliesDao = DaoManager.createDao(connectionSource, Reply.class);
		TableUtils.createTable(connectionSource, Reply.class);
	}
	
	public Dao<User, Integer> getUsersDao() {
		return this.usersDao;
	}

	public ConnectionSource getConnectionSource() {
		return connectionSource;
	}

	public Dao<Category, Integer> getCategoriesDao() {
		return categoriesDao;
	}

	public Dao<Forum, Integer> getForumsDao() {
		return forumsDao;
	}

	public Dao<Thread, Integer> getThreadsDao() {
		return threadsDao;
	}

	public Dao<Reply, Integer> getRepliesDao() {
		return repliesDao;
	}
	
}
