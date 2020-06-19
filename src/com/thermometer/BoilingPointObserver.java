package com.thermometer;

import java.util.Map;

public class BoilingPointObserver implements Observer {

    private boolean isAtBoilingPoint = false;

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
     * Checks if current temperature is at boiling point and if a notification is necessary.
     */
    private boolean shouldNotify() {

        double temperatureDifference = Math.abs(this.prevTemperature - this.temperature);
        double insignificantFluctuationBase = (this.boilingPoint - this.insignificantFluctuation);
        double insignificantFluctuationCeiling = (this.boilingPoint + this.insignificantFluctuation);

        // In between boiling point range. Insignificant fluctuations included.
        // ex. 9.5 ... 10 ... 10.5


        boolean isTempInBetweenBoilingPointRange = (this.temperature >= insignificantFluctuationBase &&
                this.temperature <= insignificantFluctuationCeiling);

        if (isTempInBetweenBoilingPointRange) {

            // Handling of temperatures below boiling point, but above insignificantFluctuationBase
            // ex. 9.5 to 9.9
            if (this.temperature >= insignificantFluctuationBase && this.temperature < this.boilingPoint) {

                if (this.isAtBoilingPoint && (temperatureDifference <= this.insignificantFluctuation)) {
                    /*
                      If previously at boiling point and current temperature is within insignificantFluctuation,
                      then this is considered an insignificant temperature fluctuation

                      SHOULD REMAIN at boiling point
                      SHOULD NOT send notification
                     */
                    this.isAtBoilingPoint = true;
                } else {
                    /*
                      If previously NOT at boiling point, then temperature is not considered an insignificant fluctuation

                      SHOULD NOT be at boiling point
                      SHOULD NOT be notified
                     */

                    this.isAtBoilingPoint = false;
                }

                return false;

            } else {
                /*
                  Temperature is at or above boiling point.
                  Use generic handler for temperature
                 */
                return checkIfTemperatureAtOrAboveBoilingPoint();
            }

        } else {
            return checkIfTemperatureAtOrAboveBoilingPoint();
        }
    }

    /**
     * Generic checker for current temperature if it is at boiling point
     */
    public boolean checkIfTemperatureAtOrAboveBoilingPoint() {
        if (temperature >= boilingPoint) {

            if (this.isAtBoilingPoint) {
                /*
                  Previous temperature and current temperature are at boiling point.

                  SHOULD STILL be at boiling point
                  SHOULD NOT be notified
                 */

                return false;
            } else {
                /*
                  Previous temperature was not at boiling point
                  Current temperature is at boiling point.

                  SHOULD BE at boiling point
                  SHOULD BE notified
                 */

                this.isAtBoilingPoint = true;
                return true;
            }

        } else {
            /*
              Current temperature is below boiling point.

              SHOULD NOT be at boiling point
              SHOULD NOT be notified
             */

            this.isAtBoilingPoint = false;
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
        return this.shouldNotify();
    }
}
