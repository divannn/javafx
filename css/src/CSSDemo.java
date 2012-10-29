import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CSSDemo extends Application {

	public static void main(String[] args) {
		Application.launch(CSSDemo.class, args);

	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		final Button btn1 = new Button("Button1");
		btn1.getStyleClass().add("button1");
		btn1.setStyle("xxx:yyy");
		root.setLeft(btn1);

		final Button btn2 = new Button("Button2");
		btn2.rotateProperty().set(45);
		root.setCenter(btn2);

		final Button btn3 = new Button("Button3");
		btn3.setId("button3");
		btn3.rotateProperty().set(-45);
		root.setRight(btn3);

		btn1.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.err.println(
						"### style selectors: " + btn1.getStyleClass());
				System.err.println(">>> style: " + btn1.getStyle());
				System.out.println("Hello from button");
			}
		});


		Scene scene = new Scene(root, 800, 600, Color.LIGHTGREEN);
		//scene.getStylesheets().add("/test1.css");
		scene.getStylesheets().add(
				CSSDemo.class.getResource("test1.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("CSS Demo");
		primaryStage.setVisible(true);
	}
}
