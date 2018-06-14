package com.App;

import javax.swing.plaf.basic.BasicBorders.SplitPaneBorder;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.Insets;
import javafx.scene.image.*;
import javafx.event.*;

public class TicTacToe extends Application {
	
	private char currentPlayer = 'X';
	private Cell[][] cell = new Cell[3][3];
	private Label statusMsg = new Label("Player X must play");
	private Label currentTime = new Label();
	private Label timeMessage = new Label();
	private VBox boxForLabels = new VBox();
	private Label labelResult;
	private Thread t;
	private Button bExit;

	public void start(Stage primaryStage) throws Exception {
		initialization(primaryStage);
	}

	public void initialization(Stage stage) {
		GridPane pane = new GridPane();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				cell[i][j] = new Cell();
				pane.add(cell[i][j], i, j);
			}
		}

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(pane);
		borderPane.setBottom(statusMsg);
		setFondForLabels();
		borderPane.setRight(boxForLabels);

		Timekeeper keeper = new Timekeeper(this);
		t = new Thread(keeper);
		t.start();

		Scene scene = new Scene(borderPane, 800, 500);
		stage.setTitle("TicTacToe application");
		stage.setScene(scene);
		stage.show();
	}


	public void updateTime(String timeCounter) {
		currentTime.setText(timeCounter);
	}

	public boolean checkIfTheBoardIsFull() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (cell[i][j].getPlayer() == ' ') {
					return false;
				}
			}
		}
		return true;
	}

	public boolean hasWon(char player) {
		for (int i = 0; i < 3; i++) {
			if (cell[i][0].getPlayer() == player && cell[i][1].getPlayer() == player
					&& cell[i][2].getPlayer() == player) {
				return true;
			}
		}
		for (int i = 0; i < 3; i++) {
			if (cell[0][i].getPlayer() == player && cell[1][i].getPlayer() == player
					&& cell[2][i].getPlayer() == player) {
				return true;
			}
		}
		if (cell[0][0].getPlayer() == player && cell[1][1].getPlayer() == player && cell[2][2].getPlayer() == player) {
			return true;
		}
		if (cell[0][2].getPlayer() == player && cell[1][1].getPlayer() == player && cell[2][0].getPlayer() == player) {
			return true;
		}
		return false;
	}

	public void setFondForLabels() {
		currentTime.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		timeMessage.setText("Game Time:");
		timeMessage.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		boxForLabels.getChildren().addAll(timeMessage, currentTime);
		boxForLabels.setAlignment(Pos.CENTER);
	}

	public void createResultWindow(char winner) {
		labelResult = new Label();
		labelResult.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 36));
		labelResult.setTextFill(Color.BLUEVIOLET);
		labelResult.setAlignment(Pos.CENTER);

		if (currentPlayer == 'D') {
			labelResult.setText("Draw !!!");
		} else {
			labelResult.setText("Player " + winner + " won !!!");
		}

		bExit = new Button("Exit");
		bExit.setOnMouseClicked(event -> {
			System.exit(0);
		});
		bExit.setTextFill(Color.DARKRED);

		VBox vBox2 = new VBox();
		vBox2.setAlignment(Pos.TOP_CENTER);
		vBox2.setPadding(new Insets(20, 20, 20, 20));
		vBox2.getChildren().addAll(labelResult, bExit);
		vBox2.setSpacing(30);
		vBox2.setStyle("-fx-background-color: chocolate");

		Scene scene2 = new Scene(vBox2, 350, 150);
		Stage stage2 = new Stage();
		stage2.setTitle("Menu");
		stage2.setScene(scene2);
		stage2.show();
	}

	public class Cell extends Pane {
		private char player = ' ';

		Cell() {
			setStyle("-fx-border-color: black");
			setPrefSize(300, 300);
			this.setOnMouseClicked(event -> {
				if (player == ' ' && currentPlayer != ' ') {
					setPlayer(currentPlayer);
					if (hasWon(currentPlayer)) {
						createResultWindow(currentPlayer);
						currentPlayer = ' ';
						t.interrupt();

					} else if (checkIfTheBoardIsFull()) {
						currentPlayer = 'D';
						createResultWindow(currentPlayer);
						currentPlayer = ' ';
						t.interrupt();
					} else {
						currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
						statusMsg.setText(currentPlayer + " player turn");
					}
				}
			});
		}

		public char getPlayer() {
			return player;
		}

		public void setPlayer(char player) {
			this.player = player;
			if (player == 'X') {
				Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
				line1.endXProperty().bind(this.widthProperty().subtract(10));
				line1.endYProperty().bind(this.heightProperty().subtract(10));

				Line line2 = new Line(10, getHeight() - 10, this.getWidth() - 10, 10);
				line2.endXProperty().bind(this.widthProperty().subtract(10));
				line2.startYProperty().bind(this.heightProperty().subtract(10));

				getChildren().addAll(line1, line2);
			} else if (player == 'O') {
				Ellipse ellipse = new Ellipse(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 10,
						getHeight() / 2 - 10);

				ellipse.centerXProperty().bind(this.widthProperty().divide(2));
				ellipse.centerYProperty().bind(this.heightProperty().divide(2));
				ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
				ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
				ellipse.setStroke(Color.BLACK);
				ellipse.setFill(Color.BLUE);

				getChildren().add(ellipse);
			}
		}
	}

}
