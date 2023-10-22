module com.example.dice_roller {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;


	opens com.example.dice_roller to javafx.fxml;
	exports com.example.dice_roller;
}