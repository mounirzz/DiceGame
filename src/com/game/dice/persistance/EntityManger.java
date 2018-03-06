package com.game.dice.persistance;

import java.util.Map;

public interface EntityManger {
	Map<String,String> charger();
	void save(int score,String pseudo);
}
