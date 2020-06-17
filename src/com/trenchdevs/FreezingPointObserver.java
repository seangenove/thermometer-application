package com.trenchdevs;

import java.util.Map;

public class FreezingPointObserver implements Observer {
    private double freezingPoint;
    private double temperature;
    private double insignificantFluctuation;
    private double prevTemperature;

    public FreezingPointObserver(Subject thermometer) {
        thermometer.registerObserver(this);
    }

    @Override
    public void update(Map<String, Double> thermometerProperties) {
        this.temperature              = thermometerProperties.get(Thermometer.TEMPERATURE_KEY);
        this.prevTemperature          = thermometerProperties.get(Thermometer.PREV_TEMPERATURE_KEY);
        this.freezingPoint            = thermometerProperties.get(Thermometer.FREEZING_POINT_KEY);
        this.insignificantFluctuation = thermometerProperties.get(Thermometer.INSIGNIFICANT_FLUCTUATION_KEY);

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

                    if((Math.abs(prevTemperature - temperature) == insignificantFluctuation)) {
                        message = "Temperature fluctuated by " + insignificantFluctuation + " C. Still at freezing point.";
                    } else {
                        message = "Temperature at freezing point!";
                    }

                }

            } else {

                if((Math.abs(prevTemperature - temperature) == insignificantFluctuation)) {
                    message = "Temperature fluctuated by " + insignificantFluctuation + " C. Still at freezing point.";
                } else {
                    message = "Temperature beyond freezing point!";
                }
            }

        } else {

            if(temperature <= freezingPoint && (Math.abs(prevTemperature - temperature) == insignificantFluctuation)) {
                message = "Temperature fluctuated by " + insignificantFluctuation + " C. Still at freezing point.";
            } else {
                return;
            }

        }

        System.out.println(String.format(messageTemplate, message));

    }
}
