import com.trenchdevs.Thermometer;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThermometerTest {

    Thermometer t;
    protected double temperature, boilingPoint, freezingPoint, threshold;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @BeforeEach
    public void setUp() {
        temperature = 5.0;
        boilingPoint = 10.0;
        freezingPoint = 0.0;
        threshold = 0.5;

        t = new Thermometer(temperature, boilingPoint, freezingPoint, threshold);
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @AfterEach
    public void tearDown() {
        t = null;
    }

    @Test
    void shouldGetValidTemperature() {
        assertEquals(temperature, t.getTemperature());
    }

    @Test
    void shouldSetValidTemperature() {
        final double newTemperature = 6.0;
        t.setTemperature(newTemperature);

        assertEquals(newTemperature, t.getTemperature());
    }

    @Test
    void shouldSetSameValidTemperature() {
        t.setTemperature(temperature);

        assertEquals(temperature, t.getTemperature());
    }

    @Test
    void shouldGetValidPrevTemperature() {
        final double newTemperature = 6.0;
        t.setTemperature(newTemperature);

        assertEquals(temperature, t.getPrevTemperature());
    }

    @Test
    void shouldGetValidBoilingPoint() {
        assertEquals(boilingPoint, t.getBoilingPoint());
    }

    @Test
    void shouldSetValidBoilingPoint() {
        final double newBoilingPiont = 11.0;
        t.setBoilingPoint(newBoilingPiont);

        assertEquals(newBoilingPiont, t.getBoilingPoint());
    }

    @Test
    void shouldGetValidFreezingPoint() {
        assertEquals(freezingPoint, t.getFreezingPoint());
    }

    @Test
    void shouldSetValidFreezingPoint() {
        final double newFreezingPoint = 11.0;
        t.setFreezingPoint(newFreezingPoint);

        assertEquals(newFreezingPoint, t.getFreezingPoint());
    }

    @Test
    void shouldGetValidThreshold() {
        assertEquals(threshold, t.getThreshold());
    }

    @Test
    void shouldSetValidThreshold() {
        final double newThreshold = 11.0;
        t.setThreshold(newThreshold);

        assertEquals(newThreshold, t.getThreshold());
    }
}