package com.trenchdevs;

import java.util.Map;

public class FreezingPointObserver implements Observer {

    private boolean isAtfreezingPoint = false;
    private boolean shouldNotify = false;

    private double temperature;
    private double prevTemperature;
    private double beforePrevTemperature;
    private double freezingPoint;
    private double insignificantFluctuation;


    public FreezingPointObserver(Subject thermometer) {
        thermometer.registerObserver(this);
    }

    @Override
    public void update(Map<String, Double> thermometerProperties) {
        this.temperature              = thermometerProperties.get(Thermometer.TEMPERATURE_KEY);
        this.prevTemperature          = thermometerProperties.get(Thermometer.PREV_TEMPERATURE_KEY);
        this.beforePrevTemperature = thermometerProperties.get(Thermometer.BEFORE_PREV_TEMPERATURE_KEY);
        this.freezingPoint            = thermometerProperties.get(Thermometer.FREEZING_POINT_KEY);
        this.insignificantFluctuation = thermometerProperties.get(Thermometer.INSIGNIFICANT_FLUCTUATION_KEY);

        show();
    }

//    public void show() {
//
//        String message = "";
//        String messageTemplate = "Freezing Point Observer: %s";
//
//        if (temperature <= freezingPoint) {
//
//            if (temperature == freezingPoint) {
//
//                if(prevTemperature == freezingPoint) {
//                    message = "SHOULD RETURN. Previous temp == current temp. Still at freezing point!";
//                } else {
//
//                    if((Math.abs(prevTemperature - temperature) == insignificantFluctuation)) {
//                        message = "Temperature fluctuated by " + insignificantFluctuation + " C. Still at freezing point.";
//                    } else {
//                        message = "Temperature at freezing point!";
//                    }
//
//                }
//
//            } else {
//
//                if((Math.abs(prevTemperature - temperature) == insignificantFluctuation)) {
//                    message = "Temperature fluctuated by " + insignificantFluctuation + " C. Still at freezing point.";
//                } else {
//                    message = "Temperature beyond freezing point!";
//                }
//            }
//
//        } else {
//
//            if(temperature <= freezingPoint && (Math.abs(prevTemperature - temperature) == insignificantFluctuation)) {
//                message = "Temperature fluctuated by " + insignificantFluctuation + " C. Still at freezing point.";
//            } else {
//                return;
//            }
//
//        }
//
//        System.out.println(String.format(messageTemplate, message));
//
//    }

    @Override
    public void show() {

        checkIfTemperaturesIsAtFreezingPoint();

        if (shouldNotify) {
            System.out.println("Temperature at freezing point!");
        } else {
            System.out.println("");
        }

    }

    private void checkIfTemperaturesIsAtFreezingPoint() {

        double tempDifference = Math.abs(prevTemperature - temperature);
        boolean isFluctuationAroundFreezingPoint = ((temperature - insignificantFluctuation) == freezingPoint ||
                temperature + insignificantFluctuation == freezingPoint);

        // handle fluctuations AROUND freezing point   ex. 9.5 -> 10  OR  10 -> 10.5   (10 is boiling point)
        if (tempDifference == insignificantFluctuation && (isFluctuationAroundFreezingPoint || temperature == freezingPoint)) {

            if ((beforePrevTemperature == temperature) || isFluctuationAroundFreezingPoint) {
                shouldNotify = false;
            } else {
                shouldNotify = true;
            }

            return;
        }

        // handle all temperature at or below freezing point
        if (temperature <= freezingPoint) {

            if (isAtfreezingPoint) {
                shouldNotify = false;
            } else {
                isAtfreezingPoint = true;
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
