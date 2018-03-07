package com.game.dice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/ihm/login_form.fxml"));
		primaryStage.setTitle("DiceGame");
		
		Scene scene = new Scene(root,291 ,324);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
