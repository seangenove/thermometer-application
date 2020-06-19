package com.thermometer;

/**
 * Subject interface.
 *
 * @author Sean Genove
 * @version 1.0.0, 06/19/2020
 */
public interface Subject {

    /**
     * Appends the specified observer to the Subject's list of observers.
     *
     * @param observer observer to be appended to the Subject's list
     */
    void registerObserver(Observer observer);

    /**
     * Sends latest Thermometer property values to all registered Thermometer observers
     */
    void notifyObservers();
}
