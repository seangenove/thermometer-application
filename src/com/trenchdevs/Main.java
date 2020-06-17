package com.trenchdevs;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the Thermometer Application!");

//        Thermometer thermometer = getUserInput();
        Thermometer thermometer = getTestInput();
        System.out.println(thermometer.toString());

        BoilingPointObserver boilingPointObserver = new BoilingPointObserver(thermometer);
        FreezingPointObserver freezingPointObserver = new FreezingPointObserver(thermometer);

        // Test Boiling Point
        thermometer.setTemperature(9.5);
        thermometer.setTemperature(10);
        thermometer.setTemperature(9.5);
        thermometer.setTemperature(10);
        thermometer.setTemperature(5);
//        thermometer.setTemperature(10.5);
//        thermometer.setTemperature(10);
//        thermometer.setTemperature(11);
//        thermometer.setTemperature(11);


////        // Test Freezing Point
        thermometer.setTemperature(0);
        thermometer.setTemperature(-0.5);
        thermometer.setTemperature(1);
        thermometer.setTemperature(0.5);
        thermometer.setTemperature(0.5);
//        thermometer.setTemperature(-0.0);
        thermometer.setTemperature(-0.5);

    }

    public static Thermometer getUserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter boiling point: ");
        double boilingPoint = scanner.nextDouble();

        System.out.print("Enter freezing point: ");
        double freezingPoint = scanner.nextDouble();

        System.out.print("Enter threshold interval: ");
        double threshold = scanner.nextDouble();

        System.out.print("Enter temperature: ");
        double temperature = scanner.nextDouble();

        return new Thermometer(boilingPoint, freezingPoint, threshold, temperature);
    }

    public static Thermometer getTestInput() {
        return new Thermometer(5, 10, 0, .5);
    }
}
