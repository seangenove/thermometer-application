package com.trenchdevs;

import java.util.ArrayList;
import java.util.List;

public class Thermometer implements Subject {
    private List<Observer> observers = new ArrayList<>();

    private double temperature;
    private double boilingPoint;
    private double freezingPoint;
    private double insignificantFluctuation = 0.5;

    private double prevTemperature;
    private double prevBoilingPoint;
    private double prevFreezingPoint;
    private double prevInsignificantFluctuation;

    private String template = "\n======== Thermometer Data ========\n" +
            "  Temperature                = %.2f C\n\n" +
            "  Boiling Point              = %.2f C\n" +
            "  Freezing Point             = %.2f C\n" +
            "  Insignificant Fluctuation  = %.2f C\n" +
            "==================================";

    public Thermometer(double temperature, double boilingPoint, double freezingPoint, double insignificantFluctuation) {
        this.temperature = temperature;
        this.boilingPoint = boilingPoint;
        this.freezingPoint = freezingPoint;
        this.insignificantFluctuation = insignificantFluctuation;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);

        // Send update for new observer
        observer.update(this);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public String toString() {

        return String.format(template,
                this.getTemperature(),
                this.getBoilingPoint(),
                this.getFreezingPoint(),
                this.getInsignificantFluctuation()
        );
        
    }

    public double getBoilingPoint() {
        return this.boilingPoint;
    }

    public double getFreezingPoint() {
        return this.freezingPoint;
    }

    public double getPrevTemperature() {
        return this.prevTemperature;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public double getInsignificantFluctuation() {
        return this.insignificantFluctuation;
    }

    public void setBoilingPoint(double boilingPoint) {
        this.prevBoilingPoint = this.boilingPoint;
        this.boilingPoint = boilingPoint;

        memberChange("boilingPoint", this.prevBoilingPoint, this.boilingPoint);
    }

    public void setFreezingPoint(double freezingPoint) {
        this.prevFreezingPoint = this.freezingPoint;
        this.freezingPoint = freezingPoint;

        memberChange("freezingPoint", this.prevFreezingPoint, this.freezingPoint);
    }

    public void setTemperature(double temperature) {

        if (this.temperature == temperature) {
            memberChange("temperature", temperature, this.temperature);
        } else {
            this.prevTemperature = this.temperature;
            this.temperature = temperature;

            memberChange("temperature", this.prevTemperature, this.temperature);
        }
    }

    public void setInsignificantFluctuation(double insignificantFluctuation) {
        this.prevInsignificantFluctuation = this.insignificantFluctuation;
        this.insignificantFluctuation = insignificantFluctuation;

        memberChange("insignificantFluctuation", this.prevInsignificantFluctuation, this.getInsignificantFluctuation());
    }

    private void memberChange(String variableName, double prevValue, double currentValue) {
        String template = "\n* %s change: %.2f C to %.2f C *";

        System.out.println(String.format(template, variableName, prevValue, currentValue));

        notifyObservers();
    }
}
