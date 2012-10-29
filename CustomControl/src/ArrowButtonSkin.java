import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;

public class ArrowButtonSkin implements Skin<ArrowButton>, ArrowButtonAPI {
	static final double ARROW_TIP_WIDTH = 5;

	ArrowButton control;
	String text = "";

	Group rootNode = new Group();
	Label lbl = null;
	int direction = ArrowButtonAPI.RIGHT;
	EventHandler clientEH = null;

	public ArrowButtonSkin(ArrowButton control) {
		this.control = control;
		draw();
	}

	public ArrowButton getControl() {
		return control;
	}

	@Override
	public ArrowButton getSkinnable() {
		return null;
	}

	public Node getNode() {
		return rootNode;
	}

	public void dispose() {
	}

	//////////////////////////////////////////////////////////////


	public void draw() {
	}

	public void setText(String text) {
		this.text = text;
		lbl.setText(text);
		// update button
		draw();
	}

	public void setOnMouseClicked(EventHandler eh) {
		clientEH = eh;
	}

	public void setDirection(int direction) {
		this.direction = direction;

		// update button
		draw();
	}

}
