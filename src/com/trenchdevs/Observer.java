package com.trenchdevs;

import java.util.Map;

public interface Observer {
    void update(Map<String, Double> thermometerProperties);
    void show();
}
