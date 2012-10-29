import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


/**
 * @author idanilov
 */
public class CaptureBubbleTest extends Application {

	public static void main(String[] args) {
		Application.launch(CaptureBubbleTest.class, args);
	}

	@Override
	public void start(final Stage primaryStage) {
		//Group root = new Group();
		FlowPane root = new FlowPane();
		root.setPadding(new Insets(10,10,10,10));
		//capture
		root.addEventFilter(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						System.err.println(">>> root capture");
						//mouseEvent.consume();
					}
				});
		//bubble
		root.addEventHandler(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						System.err.println(">>> root bubble");
					}
				});
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				//System.err.println(">>> root self");
			}
		});

		Rectangle r = new Rectangle(10, 10, 100, 50);
		r.setFill(Color.BLUE);
		//r.setMouseTransparent(true);
		r.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				System.err.println("+++ rect self");
			}
		});
		root.getChildren().add(r);

		Scene scene = new Scene(root, 600, 400, Color.LIGHTGREEN);
		primaryStage.setTitle("CaptureBubbleTest");
		primaryStage.setScene(scene);
		primaryStage.setVisible(true);
	}
}

