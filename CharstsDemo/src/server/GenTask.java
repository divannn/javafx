package server;

import model.ChartData;

import java.util.TimerTask;

public class GenTask extends TimerTask {

    private ChartData chartData;
    private long start = -1;
    private long time = 1000 * (System.currentTimeMillis() / 1000);

    public GenTask(ChartData data) {
        this.chartData = data;
    }

    @Override
    public void run() {
        if (start == -1) {
            start = System.currentTimeMillis();
            createInitialData();
        }

        long curr = System.currentTimeMillis();
        long elapsed = Math.round((curr - start) / 1000.0);
        update();
        if (elapsed % 3 == 0) {
            add();
        }
    }

    private void createInitialData() {
        int SIZE = 31;
        double[][] r = new double[SIZE][6];
        for (int i = 0; i < SIZE; i++) {
            double[] newData = getRandomData();
            r[i] = new double[]{time, newData[0], newData[1], newData[2],
                    newData[3], newData[4]};
            time += 1000;
        }
        chartData.addInitial(r);
    }

    private void add() {
        time += 1000;
        double[] newData = getRandomData();
        double[] dataItem = {time, newData[0], newData[1], newData[2],
                newData[3], newData[4]};
        chartData.addData(dataItem);
    }

    private void update() {
        double[] newData = getRandomData();
        double[] dataItem = {time, newData[0], newData[1], newData[2],
                newData[3], newData[4]};
        chartData.updateData(dataItem);
    }

    private double[] getRandomData() {
        double MAX = 30.0;
        double low = Math.random() * MAX;
        double high = low + Math.random() * 20.0;
        double range = high - low;
        double open = low + Math.random() * range;
        double close = low + Math.random() * range;
        double MAX_VOLUME = 15.0;
        double volume = Math.random() * MAX_VOLUME;
        return new double[]{open, close, high, low, volume};
    }
}
