package com.example.dice_roller;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Main extends Application {

	FillTransition fillTransition;
	ImageView imageView;
	boolean rolling = false;
	Text text;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
			// Creating a stackPane which acts as the base of application
			StackPane root = new StackPane();

			// Creating a rectangle which set the background colour
			Rectangle rectangle = new Rectangle();
			rectangle.widthProperty().bind(root.widthProperty());
			rectangle.heightProperty().bind(root.heightProperty());
			const_Colour(rectangle);
			transition_Colour(rectangle);

			// Creating a button
			Button button = new Button("ROLL");
			buttonIcon(button);
			button_Functionality(button);
			root.getChildren().add(button);


			Image gifImage = new Image(getClass().getResourceAsStream("Dice.gif"));
			imageView = new ImageView(gifImage);
			imageView.setFitHeight(100);
			imageView.setFitWidth(100);

			VBox container = new VBox(10);
			text = new Text("Dice Roller");
			text.setFont(new Font(20));
			text.setFill(Color.WHITE);
			container.getChildren().addAll(text, imageView, button);
			container.setAlignment(Pos.CENTER);

			root.getChildren().addAll(rectangle, container);

		Scene scene = new Scene(root, 500, 500);
		primaryStage.setTitle("Rolling Dice");
		primaryStage.setScene(scene);
		primaryStage.show();

		}

		public void const_Colour(Rectangle rectangle){
			rectangle.setFill(Color.BLACK);
		}

		public void transition_Colour(Rectangle rectangle){
			Duration duration = Duration.seconds(1);
			Color[] colorCodes = {Color.BLACK, Color.SLATEGREY};
			fillTransition = new FillTransition(duration, rectangle, colorCodes[0], colorCodes[1]);
			fillTransition.setCycleCount(FillTransition.INDEFINITE);
			fillTransition.setAutoReverse(true);
		}

	public void startOrPlayTransition(boolean playOrStop) {
		if (playOrStop) {
			fillTransition.play();
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> fillTransition.stop()));
			timeline.play();
		}
	}


		public void buttonIcon(Button button) {
			button.setStyle(
							"-fx-background-color: lightgray;" +
											"-fx-border-color: black;" +
											"-fx-border-width: 0px;" +
											"-fx-border-radius: 0px;"
			);
		}

	public void button_Functionality(Button button) {
		ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
		scaleTransition.setFromX(1.0);
		scaleTransition.setFromY(1.0);
		scaleTransition.setToX(0.8);
		scaleTransition.setToY(0.8);

		button.setOnMouseClicked(event -> {
			if (!rolling) {
				System.out.println("Button clicked!");
				rolling = true;
				imageView.setVisible(true);
				imageView.setFitHeight(100);
				imageView.setFitWidth(100);
				imageView.setImage(new Image(getClass().getResourceAsStream("Dice.gif")));
				startOrPlayTransition(true);
				text.setText("Rolling...");
				scaleTransition.play();

				// Schedule changing the image after 2 seconds
				Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> changeToRandomImage()));
				timeline.play();
			}
		});

		button.setOnMouseReleased(event -> {
			rolling = false;
			scaleTransition.setRate(-1);
			scaleTransition.play();
		});
	}

	private void changeToRandomImage() {
		Random random = new Random();
		int randomIndex = random.nextInt(6) + 1; // Assuming you have images named dice1.png, dice2.png, ...
		Image newImage = new Image(getClass().getResourceAsStream("dice" + randomIndex + ".jpeg"));
		imageView.setImage(newImage);
		text.setText(String.valueOf(randomIndex));
	}
}
