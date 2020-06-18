package com.trenchdevs;

import java.util.Map;

public class BoilingPointObserver implements Observer {

    private boolean isAtBoilingPoint = false;
    private boolean shouldNotify = false;

    private double temperature;
    private double prevTemperature;
    private double boilingPoint;
    private double insignificantFluctuation;

    public BoilingPointObserver(Subject thermometer) {
        thermometer.registerObserver(this);
    }

    @Override
    public void update(Map<String, Double> thermometerProperties) {
        this.temperature = thermometerProperties.get(Thermometer.TEMPERATURE_KEY);
        this.prevTemperature = thermometerProperties.get(Thermometer.PREV_TEMPERATURE_KEY);
        this.boilingPoint = thermometerProperties.get(Thermometer.BOILING_POINT_KEY);
        this.insignificantFluctuation = thermometerProperties.get(Thermometer.INSIGNIFICANT_FLUCTUATION_KEY);

        show();
    }

    @Override
    public void show() {

        checkIfTemperatureIsAtBoilingPoint();

        if (shouldNotify) {
            System.out.print("-> Temperature reached boiling point!");
        }

    }

    private void checkIfTemperatureIsAtBoilingPoint() {

        double temperatureDifference = Math.abs(prevTemperature - temperature);
        double insignificantFluctuationBase = (boilingPoint - insignificantFluctuation);
        double insignificantFluctuationCeiling = (boilingPoint + insignificantFluctuation);

        // In between boiling point range. Insignificant fluctuations included.
        // ex. 9.5 ... 10 ... 10.5
        boolean isTempInBetweenBoilingPointRange = (temperature >= insignificantFluctuationBase &&
                temperature <= insignificantFluctuationCeiling);

        if (isTempInBetweenBoilingPointRange) {

            // Handling of temperatures below boiling point, but above insignificantFluctuationBase
            // ex. 9.5 to 9.9
            if (temperature >= insignificantFluctuationBase && temperature < boilingPoint) {

                if (isAtBoilingPoint && (temperatureDifference <= insignificantFluctuation)) {
                    /**
                     * If previously at boiling point, then this is considered insignificant fluctuation
                     *
                     * SHOULD REMAIN at boiling point
                     * SHOULD NOT send notification
                     */

                    shouldNotify = false;
                } else {
                    /**
                     * If previously NOT at boiling point, then temperature is not considered an insignificant fluctuation
                     *
                     * SHOULD NOT be at boiling point
                     * SHOULD NOT be notified
                     */

                    isAtBoilingPoint = false;
                    shouldNotify = false;
                }

            } else {
                /**
                 * Temperature is at or above boiling point.
                 * Use generic handler for temperature
                 */
                handleTemperatureAtOrAboveBoilingPoint();
            }

        } else {
            // handle all temperature at or beyond boiling point
            handleTemperatureAtOrAboveBoilingPoint();
        }

    }

    public void handleTemperatureAtOrAboveBoilingPoint() {
        if (temperature >= boilingPoint) {

            if (isAtBoilingPoint) {
                /**
                 * Previous temperature and current temperature are at boiling point.
                 *
                 * SHOULD STILL be at boiling point
                 * SHOULD NOT be notified
                 */

                isAtBoilingPoint = true;
                shouldNotify = false;
            } else {
                /**
                 * Previous temperature was not at boiling point
                 * Current temperature is at boiling point.
                 *
                 * SHOULD BE at boiling point
                 * SHOULD BE notified
                 */

                isAtBoilingPoint = true;
                shouldNotify = true;
            }

        } else {
            /**
             * Current temperature is below boiling point.
             *
             * SHOULD NOT be at boiling point
             * SHOULD NOT be notified
             */

            isAtBoilingPoint = false;
            shouldNotify = false;
        }
    }

    public boolean getShouldNotify() {
        return shouldNotify;
    }
}
