package candle;


import javafx.scene.control.Tooltip;
import javafx.scene.shape.Line;

public class Bar extends BarBase {
	private Line highLowLine = new Line();
	private Line openLine = new Line();
	private Line closeLine = new Line();
	private boolean openAboveClose = true;

	public Bar(String seriesStyleClass, String dataStyleClass) {
		super(seriesStyleClass, dataStyleClass);
		getChildren().addAll(highLowLine, closeLine, openLine);
		Tooltip.install(this, tooltip);
	}

	@Override
	public void update(double closeOffset, double highOffset, double lowOffset,
			double candleWidth) {
		openAboveClose = closeOffset > 0;
		updateStyleClasses();
		highLowLine.setStartY(highOffset);
		highLowLine.setEndY(lowOffset);

		openLine.setStartY(0);
		openLine.setEndY(0);
		openLine.setStartX(-candleWidth / 2);
		openLine.setEndX(0);

		closeLine.setStartY(closeOffset);
		closeLine.setEndY(closeOffset);
		closeLine.setStartX(0);
		closeLine.setEndX(candleWidth / 2);
	}

	protected void updateStyleClasses() {
		getStyleClass().setAll("candlestick-candle" /*,seriesStyleClass,
				dataStyleClass*/);
		highLowLine.getStyleClass().setAll("bar", /*seriesStyleClass,
				dataStyleClass,*/
				openAboveClose ? "open-above-close" : "close-above-open");
		openLine.getStyleClass().setAll("bar",
				/*dataStyleClass,*/
				openAboveClose ? "open-above-close" : "close-above-open");
		closeLine.getStyleClass().setAll("bar",
				/*dataStyleClass,*/
				openAboveClose ? "open-above-close" : "close-above-open");
	}

}

