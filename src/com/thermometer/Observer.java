package com.thermometer;

import java.util.Map;

/**
 * Observer interface.
 * @author Sean Genove
 * @version 1.0.0, 06/19/2020
 */
public interface Observer {

    /**
     * Updates Observer's Thermometer property values
     *
     * @param subjectProperties immutable HashMap containing Subject property values
     */
    void update(Map<String, Double> subjectProperties);

    /**
     * Indicator for when the Observer's threshold has been reached.
     *
     */
    void show();
}
