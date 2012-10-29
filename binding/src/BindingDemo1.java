import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


/**
 * @author idanilov
 */
public class BindingDemo1 extends Application {

	public static void main(String[] args) {
		Application.launch(BindingDemo1.class, args);
	}

	@Override
	public void start(final Stage primaryStage) {
		Group root = new Group();
		Scene scene = new Scene(root, 600, 400, Color.LIGHTGREEN);

		Slider slider = new Slider(0, 200, 100);
		slider.setLayoutY(50);
		slider.rotateProperty().set(-45);
		root.getChildren().add(slider);

		Circle circle = new Circle(100);
		root.getChildren().add(circle);
		circle.setFill(Color.AZURE);

		circle.radiusProperty().bind(slider.valueProperty());
		circle.centerXProperty().bind(scene.widthProperty().divide(2));
		circle.centerYProperty().bind(scene.heightProperty().divide(2));

		primaryStage.setTitle("BindingDemo1");
		primaryStage.setScene(scene);
		primaryStage.setVisible(true);
	}
}

