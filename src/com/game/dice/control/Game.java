package com.game.dice.control;

import java.util.Observable;

import com.game.dice.metier.Dice;
import com.game.dice.metier.Result;
import com.game.dice.persistance.EntityManger;
import com.game.dice.persistance.MySqlEntityManager;

public class Game extends Observable {

	private static class GameHolder {
		private final static Game instance = new Game(MySqlEntityManager.getInstance());
	}

	public static Game getInstance() {
		return GameHolder.instance;
	}

	private int id;
	private int round;
	private int score;
	private String pseudoJoueur;
	private Dice dice1;
	private Dice dice2;
	private EntityManger entitymanger;

	public int getId() {
		return id;
	}

	public Result play(String playerName) {
		Integer arg = null;
		int bestscore = -1;
		String pseudoBestscore = "";
		this.round++;
		this.dice1.roll();
		this.dice2.roll();

		// si la somme des dés est égale à 7 on incrémente le score de 10 points
		if (dice1.getState() + dice2.getState() == 7) {
			this.score = this.score + 10;
		}
		Result result = new Result(dice1, dice2, score);
		this.setChanged();
		this.notifyObservers(this.round+":"+(dice1.getState()+dice2.getState())+":"+this.score);
		return result ;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getPseudoJoueur() {
		return pseudoJoueur;
	}

	public void setPseudoJoueur(String pseudoJoueur) {
		this.pseudoJoueur = pseudoJoueur;
	}

	public Dice getDice1() {
		return dice1;
	}

	public void setDice1(Dice dice1) {
		this.dice1 = dice1;
	}

	public Dice getDice2() {
		return dice2;
	}

	public void setDice2(Dice dice2) {
		this.dice2 = dice2;
	}

	public EntityManger getEntitymanger() {
		return entitymanger;
	}

	public void setEntitymanger(EntityManger entitymanger) {
		this.entitymanger = entitymanger;
	}

	public Game(int id, int round, int score, String pseudoJoueur, Dice dice1, Dice dice2, EntityManger entitymanger) {
		super();
		this.id = id;
		this.round = round;
		this.score = score;
		this.pseudoJoueur = pseudoJoueur;
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.entitymanger = entitymanger;
	}

	public void init(String pseudoJoueur) {
		this.round = 0;
		this.score = 0;
		this.pseudoJoueur = pseudoJoueur;
	}
}
