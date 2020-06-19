package com.thermometer;

/**
 * Logger for Thermometer property changes.
 *
 * @author Sean Genove
 * @version 1.0.0, 06/19/2020
 */
public class PropertyChangeLogger {

    /**
     * Sole constructor
     */
    public PropertyChangeLogger() {
    }

    /**
     * Logs value changes on specified Thermometer class member
     *
     * @param member        Thermometer class member that involves value change
     * @param previousValue previous value of specified Thermometer class member
     * @param currentValue  current value of specified Thermometer class member
     */
    public void log(String member, double previousValue, double currentValue) {
        String template = "\n* %s change: %.2f C to %.2f C *";

        System.out.println(String.format(template, member, previousValue, currentValue));
    }
}
