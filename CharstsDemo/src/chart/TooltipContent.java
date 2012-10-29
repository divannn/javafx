package chart;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TooltipContent extends GridPane {
    private Label openValue = new Label();
    private Label closeValue = new Label();
    private Label highValue = new Label();
    private Label lowValue = new Label();
    private Label dateValue = new Label();

    public TooltipContent() {
        Label open = new Label("OPEN:");
        Label close = new Label("CLOSE:");
        Label high = new Label("HIGH:");
        Label low = new Label("LOW:");
        Label date = new Label("TIME:");
        open.getStyleClass().add("candlestick-tooltip-label");
        close.getStyleClass().add("candlestick-tooltip-label");
        high.getStyleClass().add("candlestick-tooltip-label");
        low.getStyleClass().add("candlestick-tooltip-label");
        date.getStyleClass().add("candlestick-tooltip-label");
        setConstraints(open, 0, 0);
        setConstraints(openValue, 1, 0);
        setConstraints(close, 0, 1);
        setConstraints(closeValue, 1, 1);
        setConstraints(high, 0, 2);
        setConstraints(highValue, 1, 2);
        setConstraints(low, 0, 3);
        setConstraints(lowValue, 1, 3);
        setConstraints(date, 0, 4);
        setConstraints(dateValue, 1, 4);
        getChildren().addAll(open, openValue, close, closeValue, high, highValue, low, lowValue, date, dateValue);
    }

    public void update(double open, double close, double high, double low, long date) {
        openValue.setText(HLOCChart.formatHLabel(open));
        closeValue.setText(HLOCChart.formatHLabel(close));
        highValue.setText(HLOCChart.formatHLabel(high));
        lowValue.setText(HLOCChart.formatHLabel(low));
        dateValue.setText(HLOCChart.formatVLabel(date));
    }
}
