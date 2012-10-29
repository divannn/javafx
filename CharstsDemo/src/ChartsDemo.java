import chart.ChartType;
import chart.HLOCChart;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import model.ChartData;
import server.DataGenerator;

import java.util.Date;


public class ChartsDemo extends Application {

    private ChartData data;
    private HLOCChart chart;
    private DataGenerator generator;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        data = new ChartData();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                generator.stop();
            }
        });
        stage.setScene(createScene());
        stage.setTitle("HLOC Charts Demo");
        stage.setVisible(true);
        data.setChart(chart);
        generator = new DataGenerator(data);
        generator.start();
    }

    private Scene createScene() {
        BorderPane root = new BorderPane();
        root.setTop(createButtonBar());
        chart = createChart(ChartType.CANDLE);
        root.setCenter(chart);
        Scene r = new Scene(root);
        r.getStylesheets().add("/chart.css");
        return r;
    }

    private FlowPane createButtonBar() {
        FlowPane result = new FlowPane(5, 5);
        result.setPadding(new Insets(5, 5, 5, 5));
        final CheckBox animateCheckBox = new CheckBox("Animate");
        animateCheckBox.selectedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> observableValue,
							Boolean aBoolean, Boolean aBoolean1) {
						chart.setAnimated(animateCheckBox.isSelected());
					}
				});

        ChoiceBox typeBox = new ChoiceBox();
        ObservableList<ChartType> types = FXCollections.observableArrayList(ChartType.CANDLE, ChartType.BAR);
        typeBox.setItems(types);
        typeBox.getSelectionModel().select(0);
        typeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ChartType>() {

            @Override
            public void changed(ObservableValue<? extends ChartType> observableValue,
                                ChartType barTypeOld, ChartType barTypeNew) {
                chart.removeSeries();
                chart.setType(barTypeNew);
                XYChart.Series<Number, Number> priceSeries = data.createPriceSeries();
                XYChart.Series<Number, Number> volumeSeries = data.createVolumeSeries();
                chart.setData(FXCollections.observableArrayList(priceSeries, volumeSeries));
            }
        });
		Label typeLabel = new Label("Type:");

		final CheckBox crossCheckBox = new CheckBox("Cross");
		crossCheckBox.setSelected(true);
		crossCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean1) {
				chart.showCross(crossCheckBox.isSelected());
			}
		});

		final CheckBox volCheckBox = new CheckBox("Volume");
		volCheckBox.setSelected(true);
		volCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean1) {
				chart.showVolume(volCheckBox.isSelected(), data.createVolumeSeries());
			}
		});

        result.getChildren().addAll(typeLabel, typeBox, crossCheckBox, volCheckBox, animateCheckBox);
        return result;
    }

    protected HLOCChart createChart(ChartType type) {
        final NumberAxis yAxis = createVAxis();
        final NumberAxis xAxis = createHAxis();
        final HLOCChart result = new HLOCChart(xAxis, yAxis);
        result.setType(type);
        result.setData(
                FXCollections.<XYChart.Series<Number, Number>>emptyObservableList());
        return result;
    }

    protected NumberAxis createVAxis() {
        //NumberAxis yAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis(0, 55, 5);
        //yAxis.setLabel("Price");
        yAxis.setSide(Side.RIGHT);
        yAxis.setTickMarkVisible(false);
        yAxis.setMinorTickVisible(false);
        return yAxis;
    }

    protected NumberAxis createHAxis() {
        //NumberAxis xAxis = new NumberAxis(0, 32, 5);
        final NumberAxis xAxis = new NumberAxis();
        //xAxis.setLabel("Time");
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);
        xAxis.setMinorTickCount(0);
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                Date d = new Date(number.longValue());
                return d.getMinutes() + ":" + d.getSeconds();
            }

            @Override
            public Number fromString(String s) {
                return null;
            }
        });
        return xAxis;
    }

}
