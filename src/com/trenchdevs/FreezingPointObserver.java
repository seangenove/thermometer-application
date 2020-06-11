package com.trenchdevs;

public class FreezingPointObserver implements Observer {
    private double freezingPoint;
    private double temperature;
    private double threshold;
    private double prevTemperature;

    public FreezingPointObserver(Subject thermometer) {
        thermometer.registerObserver(this);
    }

    @Override
    public void update(Thermometer thermometer) {
        this.freezingPoint   = thermometer.getFreezingPoint();
        this.threshold       = thermometer.getThreshold();
        this.temperature     = thermometer.getTemperature();
        this.prevTemperature = thermometer.getPrevTemperature();

        show();
    }

    public void show() {

        String message = "";
        String messageTemplate = "Freezing Point Observer: %s";

        if (temperature <= freezingPoint) {

            if (temperature == freezingPoint) {

                if(prevTemperature == freezingPoint) {
                    message = "SHOULD RETURN. Previous temp == current temp. Still at freezing point!";
                } else {

                    if((Math.abs(prevTemperature - temperature) == threshold)) {
                        message = "Temperature fluctuated by " + threshold + " C. Still at freezing point.";
                    } else {
                        message = "Temperature at freezing point!";
                    }

                }

            } else {

                if((Math.abs(prevTemperature - temperature) == threshold)) {
                    message = "Temperature fluctuated by " + threshold + " C. Still at freezing point.";
                } else {
                    message = "Temperature beyond freezing point!";
                }
            }

        } else {

            if(temperature <= freezingPoint && (Math.abs(prevTemperature - temperature) == threshold)) {
                message = "Temperature fluctuated by " + threshold + " C. Still at freezing point.";
            } else {
                return;
            }

        }

        System.out.println(String.format(messageTemplate, message));

    }
}
