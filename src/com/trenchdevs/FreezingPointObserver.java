package com.trenchdevs;

import java.util.Map;

public class FreezingPointObserver implements Observer {

    private boolean isAtFreezingPoint = false;
    private boolean shouldNotify = false;

    private double temperature;
    private double prevTemperature;
    private double freezingPoint;
    private double insignificantFluctuation;

    public FreezingPointObserver(Subject thermometer) {
        thermometer.registerObserver(this);
    }

    @Override
    public void update(Map<String, Double> thermometerProperties) {
        this.temperature = thermometerProperties.get(Thermometer.TEMPERATURE_KEY);
        this.prevTemperature = thermometerProperties.get(Thermometer.PREV_TEMPERATURE_KEY);
        this.freezingPoint = thermometerProperties.get(Thermometer.FREEZING_POINT_KEY);
        this.insignificantFluctuation = thermometerProperties.get(Thermometer.INSIGNIFICANT_FLUCTUATION_KEY);

        show();
    }

    @Override
    public void show() {

        checkIfTemperatureIsAtFreezingPoint();

        if (shouldNotify) {
            System.out.print("Temperature at freezing point!");
        }

    }

    private void checkIfTemperatureIsAtFreezingPoint() {

        double temperatureDifference = Math.abs(prevTemperature - temperature);
        double insignificantFluctuationBase = (freezingPoint + insignificantFluctuation);
        double insignificantFluctuationCeiling = (freezingPoint - insignificantFluctuation);

        // In between freezing point range. Insignificant fluctuations included.
        // ex. -0.5 ... 0 ... 0.5
        boolean isTempInBetweenFreezingPointRange = (temperature <= insignificantFluctuationBase &&
                temperature >= insignificantFluctuationCeiling);

        System.out.println(isTempInBetweenFreezingPointRange);

        if (isTempInBetweenFreezingPointRange) {

            // Handling of temperatures below freezing point, but above insignificantFluctuationBase
            // ex. 0.0 to 0.5
            if (temperature <= insignificantFluctuationBase && temperature > freezingPoint) {

                if (isAtFreezingPoint && (temperatureDifference <= insignificantFluctuation)) {
                    /**
                     * If previously at freezing point, then this is considered insignificant fluctuation
                     *
                     * SHOULD REMAIN at freezing point
                     * SHOULD NOT send notification
                     */

                    shouldNotify = false;
                } else {
                    /**
                     * If previously NOT at freezing point, then temperature is not considered an insignificant fluctuation
                     *
                     * SHOULD NOT be at freezing point
                     * SHOULD NOT be notified
                     */

                    isAtFreezingPoint = false;
                    shouldNotify = false;
                }

            } else {
                /**
                 * Temperature is at or above freezing point.
                 * Use generic handler for temperature
                 */
                handleTemperatureAtOrBelowFreezingPoint();
            }

        } else {
            // handle all temperature at or beyond freezing point
            handleTemperatureAtOrBelowFreezingPoint();
        }

    }

    public void handleTemperatureAtOrBelowFreezingPoint() {
        if (temperature <= freezingPoint) {

            if (isAtFreezingPoint) {
                /**
                 * Previous temperature and current temperature are at freezing point.
                 *
                 * SHOULD STILL be at freezing point
                 * SHOULD NOT be notified
                 */

                isAtFreezingPoint = true;
                shouldNotify = false;
            } else {
                /**
                 * Previous temperature was NOT at freezing point
                 * Current temperature is at freezing point.
                 *
                 * SHOULD BE at freezing point
                 * SHOULD BE notified
                 */

                isAtFreezingPoint = true;
                shouldNotify = true;
            }

        } else {
            /**
             * Current temperature is above freezing point.
             *
             * SHOULD NOT be at freezing point
             * SHOULD NOT be notified
             */

            isAtFreezingPoint = false;
            shouldNotify = false;
        }
    }

    public boolean getShouldNotify() {
        return shouldNotify;
    }
}
