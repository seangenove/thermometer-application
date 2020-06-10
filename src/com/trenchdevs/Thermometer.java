package com.trenchdevs;

import java.util.ArrayList;

public class Thermometer implements Subject {
    private ArrayList<Observer> observers = new ArrayList<>();

    private double boilingPoint = 10.0;
    private double freezingPoint = 0.0;
    private double threshold = 0.5;
    private double temperature = 0.0;

    private double prevBoilingPoint = 0.0;
    private double prevFreezingPoint = 0.0;
    private double prevThreshold = 0.0;
    private double prevTemperature = 0.0;

    private Thermometer() {
    }

    public Thermometer(double boilingPoint, double freezingPoint, double temperature) {
        this.boilingPoint = boilingPoint;
        this.freezingPoint = freezingPoint;
        this.temperature = temperature;
    }

    public Thermometer(double boilingPoint, double freezingPoint, double threshold, double temperature) {
        this.boilingPoint = boilingPoint;
        this.freezingPoint = freezingPoint;
        this.threshold = threshold;
        this.temperature = temperature;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);

        // Send update for new observer
        observer.update(
                this.getBoilingPoint(),
                this.getFreezingPoint(),
                this.getThreshold(),
                this.getPrevTemperature(),
                this.getTemperature()
        );
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(
                    this.getBoilingPoint(),
                    this.getFreezingPoint(),
                    this.getThreshold(),
                    this.getPrevTemperature(),
                    this.getTemperature()
            );
        }
    }

    @Override
    public String toString() {
        String template = "\n======== Thermometer Data ========\n" +
                "  Boiling Point  = %.2f C\n" +
                "  Freezing Point = %.2f C\n" +
                "  Threshold      = %.2f C\n" +
                "  Temperature    = %.2f C\n" +
                "==================================";

        return String.format(template, this.boilingPoint, this.freezingPoint, this.threshold, this.temperature);
    }

    public double getBoilingPoint() {
        return this.boilingPoint;
    }

    public double getFreezingPoint() { return this.freezingPoint; }

    public double getPrevTemperature() {
        return this.prevTemperature;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public double getThreshold() {
        return this.threshold;
    }

    public void setBoilingPoint(double boilingPoint) {
        this.prevBoilingPoint = this.boilingPoint;
        this.boilingPoint = boilingPoint;

        logChange("boilingPoint", this.prevBoilingPoint, this.boilingPoint);
        notifyObservers();
    }

    public void setFreezingPoint(double freezingPoint) {
        this.prevFreezingPoint = this.freezingPoint;
        this.freezingPoint = freezingPoint;

        logChange("freezingPoint", this.prevFreezingPoint, this.freezingPoint);
        notifyObservers();
    }

    public void setTemperature(double temperature) {
        this.prevTemperature = this.temperature;
        this.temperature = temperature;

        logChange("temperature", this.prevTemperature, this.temperature);
        notifyObservers();
    }

    public void setThreshold(double threshold) {
        this.prevThreshold = this.threshold;
        this.threshold = threshold;

        logChange("threshold", this.prevThreshold, this.threshold);
        notifyObservers();
    }

    private void logChange(String variableName, double prevValue, double currentValue) {
        String template = "\n* %s changed from %.2f C to %.2f C *";

        System.out.println(String.format(template, variableName, prevValue, currentValue));
//        System.out.println(toString());
    }
}
