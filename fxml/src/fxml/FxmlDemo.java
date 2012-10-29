package fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class FxmlDemo extends Application {

	public static void main(String[] args) {
		Application.launch(FxmlDemo.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {



        Parent form = (Parent) FXMLLoader.load(
                FxmlDemo.class.getResource("/fxml/Form.xml"),
                ResourceBundle.getBundle("fxml.Form"));



		primaryStage.setTitle("FXML Demo");
		primaryStage.setWidth(400);
		primaryStage.setHeight(300);


       // form.setScaleX(3);
		Scene scene = new Scene(form);
		primaryStage.setScene(scene);
		primaryStage.setVisible(true);
	}
}
