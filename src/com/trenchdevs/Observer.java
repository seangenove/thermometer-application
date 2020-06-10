package com.trenchdevs;

public interface Observer {
    void update(
            double boilingPoint,
            double freezingPoint,
            double threshold,
            double prevTemperature,
            double temperature
    );

    void show();
}
