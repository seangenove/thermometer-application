package com.trenchdevs;

import java.util.Map;

public class BoilingPointObserver implements Observer {

    private boolean isAtBoilingPoint = false;
    private boolean shouldNotify = false;

    private double temperature;
    private double prevTemperature;
    private double beforePrevTemperature;
    private double boilingPoint;
    private double insignificantFluctuation;

    public BoilingPointObserver(Subject thermometer) {
        thermometer.registerObserver(this);
    }

    @Override
    public void update(Map<String, Double> thermometerProperties) {
        this.temperature = thermometerProperties.get(Thermometer.TEMPERATURE_KEY);
        this.prevTemperature = thermometerProperties.get(Thermometer.PREV_TEMPERATURE_KEY);
        this.beforePrevTemperature = thermometerProperties.get(Thermometer.BEFORE_PREV_TEMPERATURE_KEY);
        this.boilingPoint = thermometerProperties.get(Thermometer.BOILING_POINT_KEY);
        this.insignificantFluctuation = thermometerProperties.get(Thermometer.INSIGNIFICANT_FLUCTUATION_KEY);

        show();
    }

    @Override
    public void show() {

        checkIfTemperaturesIsAtBoilingPoint();

        if (shouldNotify) {
            System.out.print("Temperature at boiling point!");
        } else {
            System.out.print("");
        }

    }

    private void checkIfTemperaturesIsAtBoilingPoint() {

        double tempDifference = Math.abs(prevTemperature - temperature);
        boolean isFluctuationAroundBoilingPoint = ((temperature - insignificantFluctuation) == boilingPoint ||
                temperature + insignificantFluctuation == boilingPoint);

        // handle fluctuations AROUND boiling point   ex. 9.5 -> 10  OR  10 -> 10.5   (10 is boiling point)
        if (tempDifference == insignificantFluctuation && (isFluctuationAroundBoilingPoint || temperature == boilingPoint)) {

            if ((beforePrevTemperature == temperature) || isFluctuationAroundBoilingPoint) {
                shouldNotify = false;
            } else {
                shouldNotify = true;
            }

            return;
        }

        // handle all temperature at or below boiling point
        if (temperature >= boilingPoint) {

            if (isAtBoilingPoint) {
                shouldNotify = false;
            } else {
                isAtBoilingPoint = true;
                shouldNotify = true;
            }

        } else {
            shouldNotify = false;
        }
    }

    public boolean getShouldNotify() {
        return shouldNotify;
    }
}
