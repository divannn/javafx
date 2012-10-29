package candle;

import chart.TooltipContent;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

/**
 * candle.Candle node used for drawing a candle
 */
public class Candle extends BarBase {
    private Line highLowLine = new Line();
    private Region bar = new Region();
    private int openAboveClose = 0;

    public Candle(String seriesStyleClass, String dataStyleClass) {
        super(seriesStyleClass, dataStyleClass);
        getChildren().addAll(highLowLine, bar);
        tooltip.setGraphic(new TooltipContent());
        Tooltip.install(bar, tooltip);
    }

    public void update(double closeOffset, double highOffset, double lowOffset,
                       double candleWidth) {
        if (closeOffset > -0.001 && closeOffset < 0.001) {
            openAboveClose = 0;
        } else if (closeOffset > 0) {
            openAboveClose = 1;
        } else {
            openAboveClose = -1;
        }
        updateStyleClasses();
        highLowLine.setStartY(highOffset);
        highLowLine.setEndY(lowOffset);
        if (candleWidth == -1) {
            candleWidth = bar.prefWidth(-1);
        }
        bar.setMinWidth(1);
        bar.setMinHeight(1);
        bar.setPrefWidth(candleWidth);
        if (openAboveClose == 1) {
            bar.resizeRelocate(-candleWidth / 2, 0, candleWidth, closeOffset);
            bar.setPrefHeight(closeOffset);
        } else {
            bar.resizeRelocate(-candleWidth / 2, closeOffset, candleWidth,
                    closeOffset * -1);
            bar.setPrefHeight(closeOffset * -1);
        }
    }

    protected void updateStyleClasses() {
        getStyleClass().setAll("candlestick-candle"/*, seriesStyleClass,
				dataStyleClass*/);
        highLowLine.getStyleClass().setAll("candlestick-line"/*, seriesStyleClass,
				dataStyleClass,*/);
        ObservableList barSelectors = bar.getStyleClass();
        barSelectors.clear();
        barSelectors.add("candlestick-bar");
        if (openAboveClose == 1) {
            barSelectors.add("open-above-close");
        } else if (openAboveClose == -1) {
            barSelectors.add("close-above-open");
        } else {
            barSelectors.add("equal");
        }
    }
}
