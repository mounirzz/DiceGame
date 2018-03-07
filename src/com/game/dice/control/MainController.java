package com.game.dice.control;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.game.dice.utils.ColumnModel;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sun.launcher.resources.launcher_de;

import static javafx.collections.FXCollections.observableArrayList;
import static com.game.dice.utils.NbrTours.NOMBRE_MAX_TOURS;

public class MainController implements Initializable , Observer {
	@FXML
	Button btLancerDe;
	@FXML
	ImageView imageDe1;
	@FXML
	ImageView imageDe2;
	@FXML
	Label score ;
	@FXML
	TableView<ColumnModel> tvHistoriqueLance;
	@FXML
	TableColumn<ColumnModel, String> collance ;
	@FXML
	TableColumn<ColumnModel, String> colResultat ;
	@FXML
	TableColumn<ColumnModel, String> colScore;
	private Game game ;
	private final ObservableList<ColumnModel> data= observableArrayList();


	public void initialize(URL location, ResourceBundle resources) {
			btLancerDe.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
					lancerDe();
					new Timer().schedule(new TimerTask() {
						
						@Override
						public void run() {
							Platform.runLater(new Runnable() {
								public void run() {
									afficherDe();
								}
							});
							
						}
					}, 3000);
				}
			});
			game = Game.getInstance();
			game.addObserver(this);
			game.getDice1().addObserver(new Observer() {
				public void update(Observable o,Object arg) {
					imageDe1.setImage(new Image(getClass().getResourceAsStream("/images/"+arg+".png")));
					System.out.println("dé 2 = ["+arg+"]");
				}
			});
			initTableView();
	}
	private void lancerDe() {
		btLancerDe.setDisable(true);
		imageDe1.setImage(new Image(getClass().getResourceAsStream("/images/aniamtion.gif")));
		imageDe2.setImage(new Image(getClass().getResourceAsStream("/images/aniamtion.gif")));
		score.setText(String.valueOf(0));
	}
	private void afficherDe() {
		btLancerDe.setDisable(false);
		game.play("Florian");
	}
	public void update(Observable o , Object arg) {
		System.out.println("arg = ["+arg+"]");
		String[] args = ((String)arg).split(":");
		score.setText(args[1]);
		data.clear();
		
		ColumnModel columnModel = new ColumnModel();
		columnModel.round = args[0];
		columnModel.resultat = args[1];
		columnModel.score = args[2];
		data.add(columnModel);
		tvHistoriqueLance.getItems().addAll(data);
		if (Integer.valueOf(args[0]) == NOMBRE_MAX_TOURS) {
			btLancerDe.setText("Terminer");
			btLancerDe.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							finishGame();
						}
			});
		}
	}
	public void initTableView() {
		collance.setCellValueFactory(new PropertyValueFactory<ColumnModel , String>("round"));
		colResultat.setCellValueFactory(new PropertyValueFactory<ColumnModel , String>("Resultat"));
		colScore.setCellValueFactory(new PropertyValueFactory<ColumnModel , String>("score"));
	}
	private void finishGame() {
		Parent root = null ;
		try {
			Stage primaryStage = (Stage)tvHistoriqueLance.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/ihm/end_game.fxml"));
			primaryStage.setTitle("DiceGame");
			Scene scene = new Scene(root,373 ,342);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
