package com.game.dice.persistance;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection implements DBConnection {

	static MySqlConnection mysqlConnection;
	public Connection conn ;
	@Override
	public void connection() {
		String url = "jdbc:mysql://localhost:4040/";
		String dbName="m_dicegame";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		try {
			Class.forName(driver).newInstance();
			this.conn=(Connection) DriverManager.getConnection(url+dbName, userName, password);
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
	@Override
	public Connection getConnection() {
			return conn ;
	}
	public static DBConnection getInstance() {
		if (mysqlConnection == null)
		mysqlConnection = new MySqlConnection();
		return mysqlConnection ;
	}

}
