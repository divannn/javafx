package chart;


import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class CrossLabel extends Group {

	private Label label = new Label();
	private Region back = new Region();

	CrossLabel() {
		back.getStyleClass().setAll("crossLabel");
		getChildren().addAll(back, label);
	}

	public void setText(String v) {
		label.setText(v);
		back.setPrefWidth(label.getWidth());
		back.setPrefHeight(label.getHeight());
	}

//	@Override
//	public double prefWidth(double v) {
//		return label.getWidth();
//	}
//
//	@Override
//	public double prefHeight(double v) {
//		return label.getHeight();
//	}

}
