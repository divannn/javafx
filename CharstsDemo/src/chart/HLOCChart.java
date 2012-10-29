package chart;

import candle.*;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import model.CandleStickExtraValues;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class HLOCChart extends XYChart<Number, Number> {

	//private Cross cross = new Cross();
	private Line vLine = new Line();
	private Line hLine = new Line();
	private CrossLabel hLabel = new CrossLabel();
	private CrossLabel vLabel = new CrossLabel();

	private Reg content = new Reg();

	private Label legend = new Label("GOOG - Google Inc.");
	private ChartType type;
	public static final String VOLUME_SERIES = "Volume";
	public static final String PRICE_SERIES = "Price";

	public HLOCChart(Axis<Number> xAxis,
			Axis<Number> yAxis) {
		super(xAxis, yAxis);
		this.type = type;

		//setPadding(new Insets(20, 20, 20, 20));

		xAxis.setAnimated(false);
		yAxis.setAnimated(false);
		setAnimated(false);

		legend.getStyleClass().add("legend");
		hLine.getStyleClass().add("cross");
		hLine.setSmooth(false);
		vLine.getStyleClass().add("cross");
		vLine.setSmooth(false);

		hLine.setMouseTransparent(true);
		vLine.setMouseTransparent(true);

		getChartContent().setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				updateCross(mouseEvent.getX(), mouseEvent.getY());
			}
		});
		getChartContent().setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				setCrossVisible(false);
			}
		});
		getChartContent().setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				updateCross(mouseEvent.getX(), mouseEvent.getY());
				setCrossVisible(true);
			}
		});
//        getPlotChildren().add(cross);

//		getPlotChildren().add(hLine);
//		getPlotChildren().add(vLine);

		//content.setAutoSizeChildren(false);

		/*content.getStyleClass().add("plot-content");*/

		getPlotChildren().add(content);

		getChartContentChildren().add(legend);
		getChartContentChildren().addAll(hLine, vLine);

		getChartContentChildren().addAll(hLabel, vLabel);

		System.err.println("CH " + getChildren());
		System.err.println("CC " + getChartChildren());
		System.err.println("PL " + getPlotChildren());
	}

	public HLOCChart(Axis<Number> xAxis,
			Axis<Number> yAxis,
			ObservableList<Series<Number, Number>> data) {
		this(xAxis, yAxis);
		setData(data);
	}

	public void setType(ChartType type) {
		this.type = type;
	}

	public void removeSeries() {
		if (getData() == null) {
			return;
		}
		for (int seriesIndex = 0;
			 seriesIndex < getData().size(); seriesIndex++) {
			getData().remove(seriesIndex);
		}
	}

	private Region getChartContent() {
		return content;
		//return (Region) (getChildren().get(1));
	}

	private ObservableList<Node> getChartContentChildren() {
		return content.getChildren();
		//return (Region) (getChildren().get(1));
	}


	private void updateCross(double x, double y) {
		hLine.setStartX(0);
		hLine.setEndX(HLOCChart.this.getWidth());
		hLine.setStartY(y);
		hLine.setEndY(y);

		vLine.setStartY(0);
		vLine.setEndY(HLOCChart.this.getHeight());
		vLine.setStartX(x);
		vLine.setEndX(x);

		String hStr = formatHLabel(
				getYAxis().getValueForDisplay(y).doubleValue());
		hLabel.setText(hStr);
		hLabel.setLayoutX(getXAxis().getWidth() - hLabel.prefWidth(0));
		hLabel.setLayoutY(y - hLabel.prefHeight(0) / 2);


		String vStr = formatVLabel(
				getXAxis().getValueForDisplay(x).longValue());
		vLabel.setText(vStr);
		vLabel.setLayoutX(x - hLabel.prefWidth(0) / 2);
		vLabel.setLayoutY(getYAxis().getHeight() - hLabel.prefHeight(0));

//        vLine.toFront();
//        hLine.toFront();
	}

	public static String formatHLabel(double v) {
		//v = Math.round(v * 100.0) / 100;
		double s = Math.pow(10, 2);
		v = Math.round(v * s) / s;
		return v + "";
	}

	public static String formatVLabel(long time) {
		Date d = new Date(time);
		return d.getMinutes() + ":" + d.getSeconds();
	}

	private void setCrossVisible(boolean v) {
		hLine.setVisible(v);
		vLine.setVisible(v);
		hLabel.setVisible(v);
		vLabel.setVisible(v);
	}

	public void showCross(boolean v) {
		if (v) {
			getChartContentChildren().addAll(hLine, vLine);
			getChartContentChildren().addAll(hLabel, vLabel);
		} else {
			getChartContentChildren().removeAll(hLine, vLine);
			getChartContentChildren().removeAll(hLabel, vLabel);
		}
	}

	public void showVolume(boolean v, XYChart.Series s) {
		showVolume = v;
		if (v) {
			getData().add(s);
		} else {
			getData().remove(1);
		}
	}

	private boolean showVolume = true;

	public boolean isShowVolume() {
		return showVolume;
	}

	// -------------- METHODS ------------------------------------------------------------------------------------------

	/**
	 * Called to update and layout the content for the plot
	 */
	@Override
	protected void layoutPlotChildren() {
		// we have nothing to layout if no data is present
		if (getData() == null) {
			return;
		}
		legend.relocate(10, 10);
		//System.err.println("-----LAYOUT");

		content.setPrefSize(getWidth(), getHeight());


		// update candle positions
		for (int seriesIndex = 0;
			 seriesIndex < getData().size(); seriesIndex++) {

			Series<Number, Number> series = getData().get(seriesIndex);

			double lB = getXAxis().getLowerBound();
			double uB = getXAxis().getUpperBound();
			double step = (uB - lB) / series.getData().size();
			Path seriesPath = null;
			if (series.getNode() instanceof Path) {
				seriesPath = (Path) series.getNode();
				seriesPath.getElements().clear();
			}

			if (series.getName().equals(PRICE_SERIES)) {
				Iterator<Data<Number, Number>> iter1 = getDisplayedDataIterator(
						series);
				while (iter1.hasNext()) {
					Data<Number, Number> item = iter1.next();
					Node itemNode = item.getNode();
					processPriceNode(item, itemNode, seriesPath, step);
				}
			}

			if (series.getName().equals(VOLUME_SERIES)) {
				Iterator<Data<Number, Number>> iter2 = getDisplayedDataIterator(
						series);
				while (iter2.hasNext()) {
					Data<Number, Number> item = iter2.next();
					Node itemNode = item.getNode();
					processVolumeNode(item, itemNode, step);
				}
			}
		}
		//System.err.println(">>>>>>>> " + getInsets() + "  " + getPadding());
		//cross.resizeRelocate(0,0,100,100);
//        cross.setLayoutX(100);
//        cross.setLayoutY(100);
//        cross.resize(200,200);
	}

	private void processPriceNode(Data<Number, Number> item, Node itemNode,
			Path seriesPath, double step) {
		CandleStickExtraValues extra = (CandleStickExtraValues) item.getExtraValue();
		double x = getXAxis().getDisplayPosition(
				getCurrentDisplayedXValue(item));
		double y = getYAxis().getDisplayPosition(
				getCurrentDisplayedYValue(item));

		if (itemNode instanceof BarBase && extra != null) {
			BarBase candle = (BarBase) itemNode;
			double close = getYAxis().getDisplayPosition(
					extra.getClose());
			double high = getYAxis().getDisplayPosition(
					extra.getHigh());
			double low = getYAxis().getDisplayPosition(extra.getLow());
			// calculate candle width
			double candleWidth = -1;
			NumberAxis xa = getXAxis();
			if (getXAxis() instanceof NumberAxis) {
				Double nextBarXValue = new Double(
						getCurrentDisplayedXValue(item).doubleValue() + step);
				Double nextBarX = xa.getDisplayPosition(nextBarXValue);
				candleWidth = (nextBarX - x) * 0.6;
			}
			// update candle
			candle.update(close - y, high - y, low - y, candleWidth);
			candle.updateTooltip(item.getYValue().doubleValue(),
					extra.getClose(), extra.getHigh(), extra.getLow(),
					getCurrentDisplayedXValue(item).longValue());

			// position the candle
			candle.setLayoutX(x);
			candle.setLayoutY(y);
		}
		/*if (seriesPath != null) {
							  if (seriesPath.getElements().isEmpty()) {
								  seriesPath.getElements().add(new MoveTo(x,
										  getYAxis().getDisplayPosition(
												  extra.getAverage())));
							  } else {
								  seriesPath.getElements().add(new LineTo(x,
										  getYAxis().getDisplayPosition(
												  extra.getAverage())));
							  }
						  }*/
	}


	private void processVolumeNode(Data<Number, Number> item, Node itemNode,
			Double step) {
		CandleStickExtraValues extra = (CandleStickExtraValues) item.getExtraValue();
		double x = getXAxis().getDisplayPosition(
				getCurrentDisplayedXValue(item));
		double y = getYAxis().getDisplayPosition(
				getCurrentDisplayedYValue(item));
		double zeroY = getYAxis().getDisplayPosition(0);

		if (itemNode instanceof Volume) {
			Volume candle = (Volume) itemNode;
			// calculate candle width
			double candleWidth = -1;
			NumberAxis xa = getXAxis();
			if (getXAxis() instanceof NumberAxis) {
				Double nextBarXValue = new Double(
						getCurrentDisplayedXValue(item).doubleValue() + step);
				Double nextBarX = xa.getDisplayPosition(nextBarXValue);
				candleWidth = (nextBarX - x) * 0.6;
			}
			// update candle
			candle.update(y, zeroY, candleWidth);
//			candle.updateTooltip(item.getYValue().doubleValue(),
//					extra.getClose(), extra.getHigh(), extra.getLow(),
//					getCurrentDisplayedXValue(item).doubleValue());

			// position the candle
			candle.setLayoutX(x);
			candle.setLayoutY(y);
		}
	}

	@Override
	protected void dataItemChanged(Data<Number, Number> item) {
		//System.err.println("===CHANGED " + item.getXValue() + "  ");
	}

	@Override
	protected void dataItemAdded(Series<Number, Number> series, int itemIndex,
			Data<Number, Number> item) {
		//System.err.println("+++ITEM ADDED ");
		//Thread.dumpStack();
		getXAxis().setUpperBound(item.getXValue().doubleValue() + 1000);
		addCandle(getData().indexOf(series), item, itemIndex,
				getBarType(series));
//        // always draw average line on top
//        if (series.getNode() != null) {
//            series.getNode().toFront();
//        }
	}

	private BarType getBarType(Series series) {
		if (series.getName().equals(VOLUME_SERIES)) {
			return BarType.VOLUME;
		} else {
			return type == ChartType.BAR ? BarType.BAR : BarType.CANDLE;
		}
	}

	@Override
	protected void seriesAdded(Series<Number, Number> series, int seriesIndex) {
		System.err.println("+++SERIES ADDED " + series.getName());
		// handle any data already in series
		if (series == null) {
			return;
		}
		if (series.getName().equals("Price")) {
			getXAxis().setTickUnit(5 * 1000);
			getXAxis().setLowerBound(
					series.getData().get(0).getXValue().doubleValue() - 1000);
			int n = series.getData().size();
			getXAxis().setUpperBound(
					series.getData().get(n - 1).getXValue().doubleValue() +
							1000);


			for (int j = 0; j < series.getData().size(); j++) {
				Data item = series.getData().get(j);
				addCandle(seriesIndex, item, j, getBarType(series));
			}
			// createDataItem series path
//			Path seriesPath = new Path();
//			seriesPath.getStyleClass().setAll("candlestick-average-line",
//					"series" + seriesIndex);
//			series.setNode(seriesPath);
			//getPlotChildren().add(seriesPath);
		}
		if (series.getName().equals(VOLUME_SERIES)) {
			for (int j = 0; j < series.getData().size(); j++) {
				Data item = series.getData().get(j);
				addCandle(seriesIndex, item, j, BarType.VOLUME);
			}
		}
	}

	private void addCandle(int seriesIndex, Data item, int itemIndex,
			BarType type) {
		//ObservableList<Node> children = getPlotChildren();
		ObservableList<Node> children = getChartContentChildren();
		Node candle = createCandle(seriesIndex, item, itemIndex, type);
		if (shouldAnimate()) {
			candle.setOpacity(0);
			// fade in new candle
			FadeTransition ft = new FadeTransition(Duration.millis(500),
					candle);
			ft.setToValue(1);
			ft.play();
		}
		children.add(candle);
		candle.toBack();
	}

	@Override
	protected void dataItemRemoved(Data<Number, Number> item,
			Series<Number, Number> series) {
		//System.err.println("---ITEM REMOVED " + item.getXValue());
		final Node candle = item.getNode();
		removeCandle(candle);
	}

	@Override
	protected void seriesRemoved(Series<Number, Number> series) {
		//System.err.println("---SERIES REMOVED ");
		// removeSeries all candle nodes
		for (XYChart.Data<Number, Number> d : series.getData()) {
			removeCandle(d.getNode());
		}
	}

	protected void removeCandle(final Node candle) {
		//final ObservableList<Node> children = getPlotChildren();
		final ObservableList<Node> children = getChartContentChildren();
		if (shouldAnimate()) {
			// fade out old candle
			FadeTransition ft = new FadeTransition(Duration.millis(500),
					candle);
			ft.setToValue(0);
			ft.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					children.remove(candle);
				}
			});
			ft.play();
		} else {
			children.remove(candle);
		}
	}

	/**
	 * Create a new candle.Candle node to represent a single data item
	 *
	 * @param seriesIndex The index of the series the data item is in
	 * @param item		The data item to createDataItem node for
	 * @param itemIndex   The index of the data item in the series
	 * @return New candle node to represent the give data item
	 */
	private Node createCandle(int seriesIndex, final Data item, int itemIndex,
			BarType type) {
		Node candle = item.getNode();
		// check if candle has already been created
		if (candle instanceof BarBase) {
//            ((BarBase) candle).setSeriesAndDataStyleClasses(
//                    "series" + seriesIndex, "data" + itemIndex);
		} else {
			if (type == BarType.CANDLE) {
				candle = new Candle("series" + seriesIndex, "data" + itemIndex);
			} else if (type == BarType.BAR) {
				candle = new Bar("series" + seriesIndex, "data" + itemIndex);
			} else {
				candle = new Volume("series" + seriesIndex, "data" + itemIndex);
			}
			item.setNode(candle);
		}
		return candle;
	}

	/**
	 * This is called when the range has been invalidated and we need to update it. If the axis are auto
	 * ranging then we compile a list of all data that the given axis has to plot and call invalidateRange() on the
	 * axis passing it that data.
	 */
	@Override
	protected void updateAxisRange() {
		// For candle stick chart we need to override this method as we need to let the axis know that they need to be able
		// to cover the whole area occupied by the high to low range not just its center data value
		final Axis<Number> xa = getXAxis();
		final Axis<Number> ya = getYAxis();
		List<Number> xData = null;
		List<Number> yData = null;
		if (xa.isAutoRanging()) {
			xData = new ArrayList<Number>();
		}
		if (ya.isAutoRanging()) {
			yData = new ArrayList<Number>();
		}
		if (xData != null || yData != null) {
			for (Series<Number, Number> series : getData()) {
				for (Data<Number, Number> data : series.getData()) {
					if (xData != null) {
						xData.add(data.getXValue());
					}
					if (yData != null) {
						CandleStickExtraValues extras = (CandleStickExtraValues) data.getExtraValue();
						if (extras != null) {
							yData.add(extras.getHigh());
							yData.add(extras.getLow());
						} else {
							yData.add(data.getYValue());
						}
					}
				}
			}
			if (xData != null) {
				xa.invalidateRange(xData);
			}
			if (yData != null) {
				ya.invalidateRange(yData);
			}
		}
	}

	public NumberAxis getXAxis() {
		return (NumberAxis) super.getXAxis();
	}

	public NumberAxis getYAxis() {
		return (NumberAxis) super.getYAxis();
	}


	private static class Reg extends Region {
		@Override
		public ObservableList<Node> getChildren() {
			return super.getChildren();
		}
	}


}

