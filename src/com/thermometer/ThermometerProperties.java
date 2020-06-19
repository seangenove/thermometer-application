package com.thermometer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThermometerProperties {

    public Map<String, Double> thermometerProperties;

    public ThermometerProperties(HashMap<String, Double> thermometerProperties) {
        this.thermometerProperties = Collections.unmodifiableMap(new HashMap<String, Double>(thermometerProperties));
    }
}
