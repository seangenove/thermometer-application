package com.thermometer;
import java.util.Map;

/**
 * FreezingPointObserver class. Implements Observer interface
 *
 * @author Sean Genove
 * @version 1.0.0, 06/19/2020
 */
public class FreezingPointObserver implements Observer {

    private boolean isAtFreezingPoint = false;
    private boolean shouldNotify = false;

    private double temperature;
    private double prevTemperature;
    private double freezingPoint;
    private double insignificantFluctuation;

    public FreezingPointObserver() {
    }

    @Override
    public void update(Map<String, Double> thermometerProperties) {
        this.temperature = thermometerProperties.get(Thermometer.TEMPERATURE_KEY);
        this.prevTemperature = thermometerProperties.get(Thermometer.PREV_TEMPERATURE_KEY);
        this.freezingPoint = thermometerProperties.get(Thermometer.FREEZING_POINT_KEY);
        this.insignificantFluctuation = thermometerProperties.get(Thermometer.INSIGNIFICANT_FLUCTUATION_KEY);

//        checkFreezingPoint();
        if (shouldNotify() && this.isAtFreezingPoint) {
            show();
        }
    }

    @Override
    public void show() {
        System.out.print("-> Temperature reached freezing point!");
    }

    /**
     * Checks if current temperature is at or below freezing point and if a notification is necessary.
     */
    public boolean shouldNotify() {

        double temperatureDifference = Math.abs(this.prevTemperature - this.temperature);
        double insignificantFluctuationBase = (this.freezingPoint + this.insignificantFluctuation);

        if (this.temperature <= this.freezingPoint && !this.isAtFreezingPoint) {

            this.isAtFreezingPoint = true;
            this.shouldNotify = true;

            return true;

        } else if (this.temperature <= insignificantFluctuationBase && this.temperature > this.freezingPoint) {

            this.isAtFreezingPoint = (this.isAtFreezingPoint && (temperatureDifference <= this.insignificantFluctuation));
            this.shouldNotify = false;

            return false;

        } else {

            if (this.temperature > this.freezingPoint) {

                // Current temperature is above freezing point.
                // SHOULD NOT be at freezing point. SHOULD NOT be notified

                this.isAtFreezingPoint = false;
                this.shouldNotify = false;

                return false;
            }

            this.shouldNotify = false;
            return false;
        }
    }

    /**
     * Returns isAtFreezingPoint value
     *
     * @return isAtFreezingPoint value
     */
    public boolean getIsAtFreezingPoint() {
        return this.isAtFreezingPoint;
    }

    /**
     * Returns shouldNotify value
     *
     * @return shouldNotify value
     */
    public boolean getShouldNotify() {
        return this.shouldNotify;
    }
}
