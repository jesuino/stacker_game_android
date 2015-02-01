/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jugvale.javafx;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author william
 */
public class App extends Application {

    final int WIDTH = 600;
    final int HEIGHT = 500;

    @Override
    public void start(Stage stage) {
        final Canvas c = new Canvas();
        VBox root = new VBox(10);
        StackPane spRoot = new StackPane();
        StackPane spGame = new StackPane();
        final StackerGame game = new StackerGame(400, 300, c.getGraphicsContext2D());
        GameEngine stackerGameEngine = new GameEngine(1000, game);
        Scene s = new Scene(spRoot, WIDTH, HEIGHT);
        Label lblTitle = new Label("The Stacker Game");
        Label lblGameOver = new Label("Game Over! \nTap to play again...");
        Label lblScore = new Label();
        Label lblLevel = new Label();
        
        spRoot.getChildren().add(root);
        lblGameOver.visibleProperty().bind(game.gameOver);
        lblScore.textProperty().bind(new SimpleStringProperty("Score is ").concat(game.score));
        lblLevel.textProperty().bind(new SimpleStringProperty("Level ").concat(game.level));

        // could be done using CSS
        lblGameOver.setTextAlignment(TextAlignment.CENTER);
        lblScore.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, FontPosture.ITALIC, 25));
        lblScore.setTextFill(Color.BLUE);
        lblLevel.setTextFill(Color.GREEN);
        lblGameOver.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 35));
        lblGameOver.setEffect(new InnerShadow(10, Color.DARKRED));
        lblTitle.setEffect(new DropShadow(20, Color.RED));

        FadeTransition gameOverAnimation = new FadeTransition(Duration.millis(500), lblGameOver);
        gameOverAnimation.setFromValue(0.1);
        gameOverAnimation.setToValue(1);
        gameOverAnimation.setCycleCount(-1);
        gameOverAnimation.setAutoReverse(true);
        gameOverAnimation.play();

         EventHandler gameOverAction = new EventHandler() {

            @Override
            public void handle(Event t) {
                 game.restart();
            }
        };
        
        lblGameOver.setOnMouseClicked(gameOverAction);
        lblGameOver.setOnTouchPressed(gameOverAction);

        game.gameOver.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t1) {
                    c.setOpacity(0.3);
                } else {
                    c.setOpacity(1);
                }
            }
        });

        spGame.getChildren().addAll(c, lblGameOver);
        root.getChildren().addAll(lblTitle, spGame, lblLevel, lblScore);
        root.setAlignment(Pos.CENTER);
        lblTitle.setFont(Font.font("Arial", 30));
        s.setFill(Color.LIGHTGRAY);
        stage.setScene(s);
        stage.show();
        stackerGameEngine.start();
    }
    
    public static void main(String args[]){
        Application.launch(args);
    }

}
