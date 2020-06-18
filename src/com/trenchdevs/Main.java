package com.trenchdevs;

import java.util.Scanner;

public class Main {

    public static void setTemperatures(Thermometer t, double[] temperatures) {

        for (double temperature : temperatures) {
            t.setTemperature(temperature);
        }

    }

    public static void main(String[] args) {

        System.out.println("Welcome to the Thermometer Application!");

        Thermometer thermometer = new Thermometer(9.6, 10, 9.5, .5);
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
