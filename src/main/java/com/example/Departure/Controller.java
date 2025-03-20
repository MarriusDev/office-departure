package com.example.Departure;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.time.LocalTime;

public class Controller {

    @FXML
    private Label welcomeText;

    @FXML
    private TextField inputTimeField;

    @FXML
    private Label outputLabel;

    @FXML
    private Button calculateButton;

    @FXML
    protected void onButtonClick() {
        Departure departure = new Departure();
        String inputTime = inputTimeField.getText();

        if (departure.userInput(inputTime) == LocalTime.of(0, 0)) {
            outputLabel.setText("Špatně zadaný formát.");
            outputLabel.setStyle("-fx-text-fill: red;");
        } else {
            outputLabel.setText(departure.start(departure.userInput(inputTime)));
            outputLabel.setStyle("-fx-text-fill: green;");
        }
    }

    @FXML
    public void initialize() {

        welcomeText.setText("zadej čas příchodu (hh:mm)");
        welcomeText.setFont(new Font(20));
        welcomeText.setUnderline(true);

        inputTimeField.setMaxWidth(100);
        inputTimeField.setAlignment(Pos.CENTER);
        inputTimeField.setFont(new Font(20));
        inputTimeField.setPromptText("hh:mm");
        inputTimeField.setStyle("-fx-background-radius: 10;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0.5, 2, 2);");

        outputLabel.setFont(new Font(18));

        calculateButton.setText("VÝPOČET");
        calculateButton.setFont(new Font(18));
        calculateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 10;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0.5, 2, 2);");


        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), calculateButton);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0.9);
        scaleTransition.setToY(0.9);

        calculateButton.setOnMouseReleased(event -> {
            scaleTransition.setFromX(0.9);
            scaleTransition.setFromY(0.9);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
            onButtonClick();
        });
    }
}
