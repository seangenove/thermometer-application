package com.thermometer;

import java.util.*;

/**
 * Thermometer class.
 *
 * @author Sean Genove
 * @version 1.0.0, 06/19/2020
 */
public class Thermometer implements Subject {


    private final List<Observer> observers = new ArrayList<>();

    public static final String TEMPERATURE_KEY = "temperature";
    public static final String PREV_TEMPERATURE_KEY = "prevTemperature";
    public static final String BOILING_POINT_KEY = "boilingPoint";
    public static final String FREEZING_POINT_KEY = "freezingPoint";
    public static final String INSIGNIFICANT_FLUCTUATION_KEY = "insignificantFluctuation";

    public PropertyChangeLogger logger;

    private double temperature;
    private double prevTemperature;
    private double boilingPoint;
    private double freezingPoint;
    private double insignificantFluctuation = 0.5;

    private static final String template = "\n========== Thermometer Data ==========\n" +
            "Temperature                = %.2f C\n\n" +
            "Boiling Point              = %.2f C\n" +
            "Freezing Point             = %.2f C\n" +
            "Insignificant Fluctuation  = %.2f C\n" +
            "======================================";

    /**
     * Sole constructor.
     *
     * @param temperature              initial temperature of the Thermometer
     * @param boilingPoint             initial boiling point threshold of the Thermometer
     * @param freezingPoint            initial freezing point threshold of the Thermometer
     * @param insignificantFluctuation initial insignificant fluctuation threshold
     */
    public Thermometer(double temperature, double boilingPoint, double freezingPoint, double insignificantFluctuation) {
        this.temperature = temperature;
        this.boilingPoint = boilingPoint;
        this.freezingPoint = freezingPoint;
        this.insignificantFluctuation = insignificantFluctuation;

        this.logger = new PropertyChangeLogger();
    }

    /**
     * Registers observer to Thermometer's list of observers and sends
     * Thermometer property values via the observer's update method
     *
     * @param observer observer to register
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);

        // Send update for new observer
        observer.update(this.getProperties());
    }


    /**
     * Sends immutable HashMap containing updated Thermometer properties to all Thermometer observers
     */
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

    /**
     * Returns the Thermometer's boiling point threshold value
     *
     * @return the Thermometer's boiling point threshold value
     */
    public double getBoilingPoint() {
        return this.boilingPoint;
    }

    /**
     * Returns the Thermometer's freezing point threshold value
     *
     * @return the Thermometer's freezing point threshold value
     */
    public double getFreezingPoint() {
        return this.freezingPoint;
    }

    /**
     * Returns the Thermometer's previous temperature value
     *
     * @return the Thermometer's previous temperature value
     */
    public double getPrevTemperature() {
        return this.prevTemperature;
    }

    /**
     * Returns the Thermometer's current temperature value
     *
     * @return the Thermometer's current temperature value
     */
    public double getTemperature() {
        return this.temperature;
    }

    /**
     * Returns the Thermometer's insignificant fluctuation threshold value
     *
     * @return the Thermometer's insignificant fluctuation threshold value
     */
    public double getInsignificantFluctuation() {
        return this.insignificantFluctuation;
    }

    /**
     * Returns an immutable HashMap containing current Thermometer properties
     *
     * @return an immutable HashMap containing current Thermometer properties
     * (temperature, previousTemperature, boilingPoint, freezingPoint and insignificantFluctuation)
     */
    public Map<String, Double> getProperties() {
        HashMap<String, Double> thermometerProperties = new HashMap<String, Double>();

        thermometerProperties.put(TEMPERATURE_KEY, this.temperature);
        thermometerProperties.put(PREV_TEMPERATURE_KEY, this.prevTemperature);
        thermometerProperties.put(BOILING_POINT_KEY, this.boilingPoint);
        thermometerProperties.put(FREEZING_POINT_KEY, this.freezingPoint);
        thermometerProperties.put(INSIGNIFICANT_FLUCTUATION_KEY, this.insignificantFluctuation);

        return Collections.unmodifiableMap(new HashMap<String, Double>(thermometerProperties));
    }

    /**
     * Returns the current count of observers
     *
     * @return the current count of observers
     */
    public int getObserverCount() {
        return observers.size();
    }

    /**
     * Sets new value for boiling point threshold
     *
     * @param boilingPoint boiling point value to set
     */
    public void setBoilingPoint(double boilingPoint) {

        if (boilingPoint > this.freezingPoint) {
            double prevBoilingPoint = this.boilingPoint;
            this.boilingPoint = boilingPoint;

            this.logger.log("boilingPoint", prevBoilingPoint, this.boilingPoint);
            notifyObservers();
        }
    }

    /**
     * Sets new value for freezing point threshold
     *
     * @param freezingPoint freezing point value to set
     */
    public void setFreezingPoint(double freezingPoint) {
        if (freezingPoint < this.boilingPoint) {
            double prevFreezingPoint = this.freezingPoint;
            this.freezingPoint = freezingPoint;

            this.logger.log("freezingPoint", prevFreezingPoint, this.freezingPoint);
            notifyObservers();
        }
    }

    /**
     * Sets new value for temperature.
     *
     * @param temperature temperature value to set
     */
    public void setTemperature(double temperature) {

        if (this.temperature != temperature) {
            this.prevTemperature = this.temperature;
            this.temperature = temperature;

            System.out.print("\n" + this.temperature + " C ");
            notifyObservers();
        }
    }

    /**
     * Sets new value for insignificant fluctuation threshold
     *
     * @param insignificantFluctuation insignificant fluctuation value to set
     */
    public void setInsignificantFluctuation(double insignificantFluctuation) {
        double prevInsignificantFluctuation = this.insignificantFluctuation;
        this.insignificantFluctuation = insignificantFluctuation;

        this.logger.log("insignificantFluctuation", prevInsignificantFluctuation, this.insignificantFluctuation);
        notifyObservers();
    }
}
