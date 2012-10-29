package model;


import chart.HLOCChart;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class ChartData {

	private List<double[]> data = new ArrayList<double[]>(100);

	private HLOCChart chart;

	public void setChart(HLOCChart chart) {
		this.chart = chart;
	}

	public int size() {
		return data.size();
	}

	public void addInitial(final double[][] value) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (double[] n : value) {
					data.add(n);
				}
				XYChart.Series<Number, Number> price = createPriceSeries();
				XYChart.Series<Number, Number> volume = createVolumeSeries();
				chart.setData(FXCollections.observableArrayList(price, volume));
			}
		});
	}

	public void addData(final double[] dataItem) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				data.add(dataItem);
				chart.getData().get(0).getData().add(
						createPriceDataItem(dataItem));
				if (chart.isShowVolume()) {
					chart.getData().get(1).getData().add(createVolumeDataItem(
							dataItem));
				}
			}
		});
	}

	public void updateData(final double[] dataItem) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				int lastIndex = size() - 1;
				data.set(lastIndex, dataItem);

				//update price
				XYChart.Data<Number, Number> lastPriceDataItem = chart.getData().get(
						0).getData().get(lastIndex);
				CandleStickExtraValues extra = (CandleStickExtraValues) lastPriceDataItem.getExtraValue();
				extra.setVolume(dataItem[5]);
				extra.setLow(dataItem[4]);
				extra.setHigh(dataItem[3]);
				extra.setClose(dataItem[2]);
				lastPriceDataItem.setYValue(dataItem[1]);
				lastPriceDataItem.setXValue(dataItem[0]);

				//update volume
				if (chart.isShowVolume()) {
					XYChart.Data<Number, Number> lastVolumeDataItem = chart.getData().get(
							1).getData().get(lastIndex);
					//don't update volume.
					//lastVolumeDataItem.setYValue(dataItem[5]);
					lastVolumeDataItem.setXValue(dataItem[0]);
				}
			}
		});
	}

	public XYChart.Series<Number, Number> createPriceSeries() {
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName(HLOCChart.PRICE_SERIES);
		for (int i = 0; i < data.size(); i++) {
			double[] day = data.get(i);
			series.getData().add(createPriceDataItem(day));
		}
		return series;
	}

	public XYChart.Series<Number, Number> createVolumeSeries() {
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName(HLOCChart.VOLUME_SERIES);
		for (int i = 0; i < data.size(); i++) {
			double[] day = data.get(i);
			series.getData().add(createVolumeDataItem(day));
		}
		return series;
	}

	private XYChart.Data<Number, Number> createPriceDataItem(double[] data) {
		return new XYChart.Data<Number, Number>(data[0], data[1],
				new CandleStickExtraValues(data[2], data[3], data[4], data[5]));
	}

	private XYChart.Data<Number, Number> createVolumeDataItem(double[] data) {
		return new XYChart.Data<Number, Number>(data[0], data[5]);
	}

}
