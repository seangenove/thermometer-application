package com.trenchdevs;

import java.util.*;

public class Thermometer implements Subject {

    private List<Observer> observers = new ArrayList<>();

    public static final String TEMPERATURE_KEY = "temperature";
    public static final String PREV_TEMPERATURE_KEY = "prevTemperature";
    public static final String BOILING_POINT_KEY = "boilingPoint";
    public static final String FREEZING_POINT_KEY = "freezingPoint";
    public static final String INSIGNIFICANT_FLUCTUATION_KEY = "insignificantFluctuation";

    private double temperature;
    private double boilingPoint;
    private double freezingPoint;
    private double insignificantFluctuation = 0.5;

    private double prevTemperature;
    private double prevBoilingPoint;
    private double prevFreezingPoint;
    private double prevInsignificantFluctuation;

    private static final String template = "\n========== Thermometer Data ==========\n" +
            "Temperature                = %.2f C\n\n" +
            "Boiling Point              = %.2f C\n" +
            "Freezing Point             = %.2f C\n" +
            "Insignificant Fluctuation  = %.2f C\n" +
            "======================================";

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
        observer.update(this.getProperties());
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this.getProperties());
        }
    }

    @Override
    public String toString() {

        return String.format(template,
                this.temperature,
                this.boilingPoint,
                this.freezingPoint,
                this.insignificantFluctuation
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

        if (this.temperature != temperature) {
//            if (this.prevTemperature != 0) {
                this.beforePrevTemperature = this.prevTemperature;
//            }

            this.prevTemperature = this.temperature;
            this.temperature = temperature;

            System.out.print(this.temperature + " C ");
            notifyObservers();
        }
    }

    public void setInsignificantFluctuation(double insignificantFluctuation) {
        this.prevInsignificantFluctuation = this.insignificantFluctuation;
        this.insignificantFluctuation = insignificantFluctuation;

        memberChange("insignificantFluctuation", this.prevInsignificantFluctuation, this.insignificantFluctuation);
    }

    public Map<String, Double> getProperties() {
        HashMap<String, Double> thermometerProperties = new HashMap<String, Double>();

        thermometerProperties.put(TEMPERATURE_KEY, this.temperature);
        thermometerProperties.put(PREV_TEMPERATURE_KEY, this.prevTemperature);
        thermometerProperties.put(BOILING_POINT_KEY, this.boilingPoint);
        thermometerProperties.put(FREEZING_POINT_KEY, this.freezingPoint);
        thermometerProperties.put(INSIGNIFICANT_FLUCTUATION_KEY, this.insignificantFluctuation);

        return Collections.unmodifiableMap(new HashMap<String, Double>(thermometerProperties));
    }

    private void memberChange(String variableName, double prevValue, double currentValue) {
        String template = "\n* %s change: %.2f C to %.2f C *";

        System.out.println(String.format(template, variableName, prevValue, currentValue));
        notifyObservers();
    }

    // Methods mostly used for testing
    public int getObserverCount() {
        return observers.size();
    }
}
