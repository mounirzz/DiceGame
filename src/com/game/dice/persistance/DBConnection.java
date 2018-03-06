package com.game.dice.persistance;

import java.sql.Connection;

public interface DBConnection {
	void connection();
	Connection getConnection();
}
