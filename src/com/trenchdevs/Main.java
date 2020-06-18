package com.trenchdevs;

import java.util.Scanner;

public class Main {

    public static void setTemperatures(Thermometer t, double[] temperatures) {

        for (double temperature : temperatures) {
            t.setTemperature(temperature);
        }

    }

    public static void getNewThermometer() {

    }

    public static void main(String[] args) {

        System.out.println("Welcome to the Thermometer Application!");

        double temperature   = 5 ;
        double boilingPoint  = 10;
        double freezingPoint = 0;
        double insignificantFluctuation = 0.5;

        Thermometer thermometer = new Thermometer(temperature, boilingPoint, freezingPoint, insignificantFluctuation);
        System.out.println(thermometer.toString());

        BoilingPointObserver boilingPointObserver = new BoilingPointObserver(thermometer);
        FreezingPointObserver freezingPointObserver = new FreezingPointObserver(thermometer);

        double[] temperatures1 = {5, 6, 6.74, 10, 10.2, 9.5, 9.9, 10, 9.5, 10, 10.5, 5};
        double[] temperatures2 = {5, 1, 0, 0.5, 0, -0.5, 1, -69};
        double[] temperatures3 = {9.5, 10, 9.5};

        setTemperatures(thermometer, temperatures1);
        setTemperatures(thermometer, temperatures2);
        setTemperatures(thermometer, temperatures3);

    }


}
