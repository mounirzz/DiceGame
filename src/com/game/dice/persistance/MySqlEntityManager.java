package com.game.dice.persistance;

import java.util.Map;
import java.sql.*;
import java.util.HashMap;

public class MySqlEntityManager implements EntityManger {
	
	Factory factory = Factory.getInstance();
	DBConnection connection = factory.createDBConnection(1);
 
	static MySqlEntityManager MySqlEntityManager ;
	private static String DB_NAME = "m_dicegame";
	private static String TABLE_NAME = "best_score";
	
	public static EntityManger getInstance() {
		if (MySqlEntityManager == null)
				MySqlEntityManager = new MySqlEntityManager();
		return MySqlEntityManager;
	}

	public Map<String, String> charger() {
					Map<String, String> result = null ;
					//* Chargement du driver JDBC pour MySQL*/
					this.connection.connection();
					/** connection a la base de données */
					try {
						this.connection.getConnection();
						Statement s = this.connection.getConnection().createStatement();
						s.executeUpdate("CREATE DATABASE IF NOT EXISTS" + DB_NAME);
						s.executeUpdate("use" + DB_NAME);
						String sql = "CREATE TABLE IF NOT EXISTS `\" + TABLE_NAME + \"` ("  + "`score` int(11) NOT NULL," + "`pseudo` varchar(255) NOT NULL"
			                    + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
						s.executeUpdate(sql);
						
						String selectTableSQL = "SELECT score, pseudo from "+ TABLE_NAME + "WHERE score = (SELECT MAX(score) FROM"+TABLE_NAME+")";
						s = this.connection.getConnection().createStatement();
						ResultSet rs = s.executeQuery(selectTableSQL);
						while (rs.next()) {
							String  score = rs.getString("score");
							String pseudo = rs.getString("pseudo");
							if (score != null && pseudo != null) {
								result = new HashMap<String ,String>();
								result.put("score", String.valueOf(score));
								result.put("pseudo", pseudo);
							}
						}
					} catch (SQLException e) {
						// TODO: handle exception
						System.out.println(e);
						System.out.println("Exception .......... ");
					}finally {
						if (this.connection.getConnection() != null) 
							try {
								this.connection.getConnection().close();
							} catch (SQLException ignore) {
						}
					}
		return result ;
	}

	@Override
	public void save(int score, String pseudo) {
		this.connection.getConnection();
		
		try {
			Statement s = this.connection.getConnection().createStatement();
			s.executeUpdate("use "+ DB_NAME);
			String rqtSQL = "" ;
			
			rqtSQL ="Insert INTO"+ TABLE_NAME + "(score, pseudo) VALUES "+"("+score+",'"+ pseudo + "')";
			
			PreparedStatement preparedStatement = this.connection.getConnection().prepareStatement(rqtSQL);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			/** Gérer les éventuelles erreurs ici */
			System.out.println("Exception ............. !");
		}finally {
			if(this.connection.getConnection() != null) {
				try {
					this.connection.getConnection().close();
				} catch (SQLException e2) {
				}
			}
		}
		
	}}
