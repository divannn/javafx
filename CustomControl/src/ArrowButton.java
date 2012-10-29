import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class ArrowButton extends Control implements ArrowButtonAPI {
	private String title = "";

	public ArrowButton() {
		this.storeSkin(new ArrowButtonSkin(this));
	}

	public ArrowButton(String title) {
		this();
		this.title = title;
		ArrowButtonSkin skin = (ArrowButtonSkin) this.getSkin();
		skin.setText(title);
	}

	public void setText(String text) {
		getSkin(getSkin()).setText(text);
	}

	public void setOnMouseClicked(EventHandler eh) {
		getSkin(getSkin()).setOnMouseClicked(eh);
	}

	public void setDirection(int direction) {
		getSkin(getSkin()).setDirection(direction);
	}

	private ArrowButtonSkin getSkin(Skin skin) {
		return (ArrowButtonSkin) skin;
	}

}
