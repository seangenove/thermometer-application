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
    public void update(Thermometer thermometer) {
        this.boilingPoint    = thermometer.getBoilingPoint();
        this.threshold       = thermometer.getThreshold();
        this.temperature     = thermometer.getTemperature();
        this.prevTemperature = thermometer.getPrevTemperature();

        show();
    }

    @Override
    public void show() {

        String message = "";
        String messageTemplate = "Boiling Point Observer: %s";

        if (temperature >= boilingPoint) {

            if (temperature == boilingPoint) {

                if(prevTemperature == boilingPoint) {
                    message = "SHOULD RETURN. Previous temp == current temp. Still at boiling point!";
                } else {

                    if((Math.abs(prevTemperature - temperature) == threshold)) {
                        message = "Temperature fluctuated by " + threshold + " C. Still at boiling point.";
                    } else {
                        message = "Temperature at boiling point!";
                    }

                }

            } else {

                if((Math.abs(prevTemperature - temperature) == threshold)) {
                    message = "Temperature fluctuated by " + threshold + " C. Still at boiling point.";
                } else {
                    message = "Temperature beyond boiling point!";
                }
            }

        } else {

            if(temperature >= boilingPoint && (Math.abs(prevTemperature - temperature) == threshold)) {
                message = "Temperature fluctuated by " + threshold + " C. Still at boiling point.";
            } else {
                return;
            }

        }

        System.out.println(String.format(messageTemplate, message));

    }
}
