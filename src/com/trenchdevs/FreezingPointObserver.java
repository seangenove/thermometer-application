package com.trenchdevs;

public class FreezingPointObserver implements Observer {
    private double freezingPoint;
    private double temperature;
    private double threshold;
    private double prevTemperature;

    @Override
    public void update(
            double boilingPoint,
            double freezingPoint,
            double threshold,
            double prevTemperature,
            double temperature
    ) {
        this.freezingPoint = freezingPoint;
        this.threshold = threshold;
        this.temperature = temperature;
        this.prevTemperature = prevTemperature;

        show();
    }

    public void show() {

        String message = "";
        String messageTemplate = "Freezing Point Observer: %s";

        if (temperature == freezingPoint || (Math.abs(prevTemperature - temperature) == threshold)) {

            if((temperature == freezingPoint) && (prevTemperature == temperature)) {
                message = "Previous temp is same with current temp. Still at boiling point";
            } else if((Math.abs(prevTemperature - temperature) == threshold)) {
                message = "Temperature fluctuated with threshold of " + threshold + " C. Still at boiling point.";
            } else {
                message = "Temperature is AT boiling point!";
            }
        } else {
            return;
        }

        System.out.println(String.format(messageTemplate, message));

    }
}
