import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimeLineTest extends Application {

	public static void main(String[] args) {
		Application.launch(TimeLineTest.class, args);
	}

	@Override
	public void start(Stage primaryStage) {
		Group root = new Group();
		final Rectangle rect = new Rectangle(100, 50, 100, 50);
		rect.setFill(Color.RED);
		final Rectangle rect2 = new Rectangle(100, 250, 100, 50);
		rect2.setFill(Color.GREEN);

		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		//timeline.setAutoReverse(true);
		final long s = System.currentTimeMillis();

		EventHandler onFinished = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				long e = System.currentTimeMillis();
				System.err.println(">>>" + (e - s) / 1000.0);
			}
		};
		EventHandler onFinished2 = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				long e = System.currentTimeMillis();
				System.err.println("###" + (e - s) / 1000.0);
			}
		};
		final KeyValue kvX = new KeyValue(rect.xProperty(), 300);
		final KeyValue kvX2 = new KeyValue(rect2.xProperty(), 500);
		final KeyFrame kf = new KeyFrame(Duration.millis(1000), onFinished,	kvX);
		final KeyFrame kf2 = new KeyFrame(Duration.millis(3000), onFinished2,
				kvX2);


		timeline.getKeyFrames().addAll(kf, kf2);
		timeline.play();

		final Button btn1 = new Button("Button");
        btn1.setRotate(45);
		final Button btn2 = new Button("Stop");
		btn2.setLayoutY(40);


        final Timeline timeline2 = new Timeline();
		timeline2.setCycleCount(Timeline.INDEFINITE);
        final KeyValue fv = new KeyValue(btn1.layoutXProperty(), 300);
        final KeyFrame kf3 = new KeyFrame(Duration.millis(10000), fv);
        timeline2.getKeyFrames().add(kf3);
        timeline2.play();

		btn1.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				//timeline.setRate(timeline.getRate() * 2);
			}
		});
		btn2.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				if (timeline.getStatus() == Animation.Status.PAUSED) {
					timeline.play();
				} else {
					timeline.pause();
				}
			}
		});
		root.getChildren().addAll(btn1, btn2, rect, rect2);

		Scene scene = new Scene(root, 800, 600, Color.LIGHTGREEN);
		primaryStage.setScene(scene);
		primaryStage.setTitle("TimeLine Demo");
		primaryStage.setVisible(true);
	}
}
