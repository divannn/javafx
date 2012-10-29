import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import np.com.ngopal.control.AutoFillTextBox;

public class ControlTest extends Application {
	public static void main(String[] args) {
		Application.launch(ControlTest.class, args);
	}

	@Override
	public void start(Stage primaryStage) {
		ObservableList<String> data = FXCollections.<String>observableArrayList();
		String[] s = new String[]{"apple", "ball", "cat", "doll", "elephant",
				"fight", "georgeous", "height", "ice", "jug",
				"aplogize", "bank", "call", "done", "ego",
				"finger", "giant", "hollow", "internet", "jumbo",
				"kilo", "lion", "for", "length", "primary", "stage",
				"scene", "zoo", "jumble", "auto", "text",
				"root", "box", "items", "hip-hop", "himalaya", "nepal",
				"kathmandu", "kirtipur", "everest", "buddha", "epic", "hotel"};


		for (int j = 0; j < s.length; j++) {
			data.add(s[j]);
		}

		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);

		AutoFillTextBox<String> box = new AutoFillTextBox<String>(data);
		//box.setFilterMode(true);
		Label l = new Label("Enter text:");
		hbox.getChildren().addAll(l, box);

		Scene scene = new Scene(hbox, 300, 200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Autocomplete Demo");
		scene.getStylesheets().add("/control.css");
		primaryStage.setVisible(true);

	}

}
