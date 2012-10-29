package model;

public class CandleStickExtraValues {
    private double close;
    private double high;
    private double low;
    //private double average;
    private double volume;

    public CandleStickExtraValues(double close, double high, double low, double volume) {
        this.close = close;
        this.high = high;
        this.low = low;
		this.volume = volume;
        //this.average = average;
    }

    public double getClose() {
        return close;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

//    public double getAverage() {
//        return average;
//    }

    public double getVolume() {
        return volume;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

//    public void setAverage(double average) {
//        this.average = average;
//    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
