package com.thermometer;
import java.util.Map;

public class BoilingPointObserver implements Observer {

    private boolean isAtBoilingPoint = false;
    private boolean shouldNotify = false;

    private double temperature;
    private double prevTemperature;
    private double boilingPoint;
    private double insignificantFluctuation;

    public BoilingPointObserver() {
    }

    @Override
    public void update(Map<String, Double> thermometerProperties) {
        this.temperature = thermometerProperties.get(Thermometer.TEMPERATURE_KEY);
        this.prevTemperature = thermometerProperties.get(Thermometer.PREV_TEMPERATURE_KEY);
        this.boilingPoint = thermometerProperties.get(Thermometer.BOILING_POINT_KEY);
        this.insignificantFluctuation = thermometerProperties.get(Thermometer.INSIGNIFICANT_FLUCTUATION_KEY);

        if (shouldNotify() && this.isAtBoilingPoint) {
            show();
        }

    }

    @Override
    public void show() {
        System.out.print("-> Temperature reached boiling point!");
    }

    /**
     * Checks if current temperature is at or above boiling point and if a notification is necessary.
     */
    public boolean shouldNotify() {

        double temperatureDifference = Math.abs(this.prevTemperature - this.temperature);
        double insignificantFluctuationBase = (this.boilingPoint - this.insignificantFluctuation);

        if (this.temperature >= this.boilingPoint && !this.isAtBoilingPoint) {

            this.isAtBoilingPoint = true;
            shouldNotify = true;

            return true;

        } else if (this.temperature >= insignificantFluctuationBase && this.temperature < this.boilingPoint) {

            this.isAtBoilingPoint = (this.isAtBoilingPoint && (temperatureDifference <= this.insignificantFluctuation));
            shouldNotify = false;

            return false;

        } else {

            if (this.temperature < this.boilingPoint) {

                // Current temperature is below boiling point.
                // SHOULD NOT be at boiling point. SHOULD NOT be notified

                this.isAtBoilingPoint = false;
                this.shouldNotify = false;

                return false;
            }

            this.shouldNotify = false;
            return false;
        }
    }

    /**
     * Returns isAtBoilingPoint value
     *
     * @return isAtBoilingPoint value
     */
    public boolean getIsAtBoilingPoint() {
        return this.isAtBoilingPoint;
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
