package com.trenchdevs;

public class BoilingPointObserver implements Observer {
    private double boilingPoint;
    private double temperature;
    private double prevTemperature;
    private double threshold;

    public BoilingPointObserver(Subject thermometer) {
        thermometer.registerObserver(this);
    }

    @Override
    public void update(
            double boilingPoint,
            double freezingPoint,
            double threshold,
            double prevTemperature,
            double temperature
    ) {
        this.boilingPoint = boilingPoint;
        this.threshold = threshold;
        this.temperature = temperature;
        this.prevTemperature = prevTemperature;

        show();
    }

    @Override
    public void show() {

        String message = "";
        String messageTemplate = "Boiling Point Observer: %s";

        if (temperature == boilingPoint || (Math.abs(prevTemperature - temperature) == threshold || temperature > boilingPoint)) {

            if ((temperature == boilingPoint) && (prevTemperature == temperature)) {
                message = "Previous temp is same with current temp. Still at boiling point";
            } else if ((Math.abs(prevTemperature - temperature) == threshold) && prevTemperature == boilingPoint) {
                message = "Temperature fluctuated by " + threshold + " C. Still at boiling point.";
            } else if (temperature > boilingPoint && (temperature - boilingPoint) != threshold) {
                message = "Temperature is BEYOND boiling point!";
            } else {
                message = "Temperature is AT boiling point!";
            }
        } else {
            return;
        }

        System.out.println(String.format(messageTemplate, message));

    }

}
