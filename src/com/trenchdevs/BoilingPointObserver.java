package com.trenchdevs;

import java.util.Map;

public class BoilingPointObserver implements Observer {
    private double boilingPoint;
    private double temperature;
    private double prevTemperature;
    private double insignificantFluctuation;

    public BoilingPointObserver(Subject thermometer) {
        thermometer.registerObserver(this);
    }

    @Override
    public void update(Map<String, Double> thermometerProperties) {
        this.temperature              = thermometerProperties.get(Thermometer.TEMPERATURE_KEY);
        this.prevTemperature          = thermometerProperties.get(Thermometer.PREV_TEMPERATURE_KEY);
        this.boilingPoint             = thermometerProperties.get(Thermometer.BOILING_POINT_KEY);
        this.insignificantFluctuation = thermometerProperties.get(Thermometer.INSIGNIFICANT_FLUCTUATION_KEY);

        show();
    }

    @Override
    public void show() {

        String message = "";
        String messageTemplate = "Boiling Point Observer: %s";

        if (temperature >= boilingPoint) {

            if (temperature == boilingPoint) {

                if (prevTemperature == boilingPoint) {
//                    message = "SHOULD RETURN. Previous temp == current temp. Still at boiling point!";
                    return;
                } else {

                    if ((Math.abs(prevTemperature - temperature) == insignificantFluctuation)) {
//                        message = "Temperature fluctuated by " + insignificantFluctuation + " C. Still at boiling point.";
                        return;
                    } else {
                        message = "Temperature at boiling point!";
                    }

                }

            } else {

                if ((Math.abs(prevTemperature - temperature) == insignificantFluctuation)) {
//                    message = "Temperature fluctuated by " + insignificantFluctuation + " C. Still at boiling point.";
                    return;
                } else {
                    message = "Temperature beyond boiling point!";
                }
            }

        } else {

            if (temperature >= boilingPoint && (Math.abs(prevTemperature - temperature) == insignificantFluctuation)) {
//                message = "Temperature fluctuated by " + insignificantFluctuation + " C. Still at boiling point.";
                return;
            } else {
                return;
            }

        }

        System.out.println(String.format(messageTemplate, message));

    }
}
