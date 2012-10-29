package candle;

import javafx.collections.ObservableList;
import javafx.scene.layout.Region;

public class Volume extends BarBase {
	private Region bar = new Region();

	public Volume(String seriesStyleClass, String dataStyleClass) {
		super(seriesStyleClass, dataStyleClass);
		getChildren().addAll(bar);
		setOpacity(0.7);
		//tooltip.setGraphic(new TooltipContent());
		//Tooltip.install(bar, tooltip);
	}

	public void update(double top, double bottom, double candleWidth) {
		updateStyleClasses();
		if (candleWidth == -1) {
			candleWidth = bar.prefWidth(-1);
		}
		bar.setMinWidth(1);
		bar.setMinHeight(1);
		bar.setPrefWidth(candleWidth);
		bar.setPrefHeight(bottom - top);
		bar.resizeRelocate(-candleWidth / 2, 0, candleWidth, top);
	}

	protected void updateStyleClasses() {
		getStyleClass().setAll("candlestick-candle"/*, seriesStyleClass,
				dataStyleClass*/);
		ObservableList barSelectors = bar.getStyleClass();
		barSelectors.clear();
		barSelectors.add("candlestick-volume");
	}
}
