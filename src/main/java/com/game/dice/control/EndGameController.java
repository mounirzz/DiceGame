package com.game.dice.control;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.game.dice.persistance.EntityManger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EndGameController implements Initializable {
	@FXML
	private Label tvScore;
	@FXML
	private Button btMain;
	@FXML
	private Label tvBestPlayer;
	@FXML
	private Label tvBestScore;


	public void initialize(URL arg0, ResourceBundle arg1) {
			tvScore.setText(Game.getInstance().getScore()+"");
			btMain.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0) {
						goToMain();
				}
			});
			chargerBDD();
	}
	private void goToMain() {
		Parent root = null;
		try {
			Stage primaryStage = (Stage)btMain.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/ihm/login_form.fxml"));
			primaryStage.setTitle("DiceGame");
			Scene scene = new Scene(root,291,324);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void chargerBDD() {
		EntityManger entityManger = Game.getInstance().getEntitymanger();
		Map<String, String> highscore = entityManger.charger();
		int score = Game.getInstance().getScore();
		String pseudo = Game.getInstance().getPseudoJoueur();
		// Sauvgarder(Score)
		if(highscore ==null ||highscore.isEmpty() || Integer.parseInt(highscore.get("score"))<=score) {
			tvBestPlayer.setText(pseudo);
			tvBestPlayer.setText(String.valueOf(score));
			entityManger.save(score, pseudo);
			System.out.println("------------GAGNER--------------");
		}else {
			tvBestPlayer.setText(highscore.get("pseudo"));
			tvBestPlayer.setText(highscore.get("score"));
			System.out.println("-------------PERDU--------------");
		}
	}
}
