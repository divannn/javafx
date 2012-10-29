package server;


import model.ChartData;

import java.util.Timer;

public class DataGenerator implements Runnable {

	private Timer timer = new Timer("Data generator");

	private ChartData data;

	public DataGenerator(ChartData data) {
		this.data = data;
	}

	public void start() {
		timer = new Timer("Data generator");
		timer.schedule(new GenTask(data), 2000, 1000);
	}

	public void stop() {
		timer.cancel();
	}

	@Override
	public void run() {

	}

}
