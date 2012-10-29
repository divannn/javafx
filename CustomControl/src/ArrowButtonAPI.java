import javafx.event.EventHandler;

public interface ArrowButtonAPI {
	public static final int RIGHT = 1;
	public static final int LEFT = 2;

	public void setText(String text);

	public void setOnMouseClicked(EventHandler eh);

	public void setDirection(int direction);
}
