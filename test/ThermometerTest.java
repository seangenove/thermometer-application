import com.trenchdevs.BoilingPointObserver;
import com.trenchdevs.FreezingPointObserver;
import com.trenchdevs.Thermometer;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ThermometerTest {

    Thermometer t;
    protected double temperature, boilingPoint, freezingPoint, insignificantFluctuation;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @BeforeEach
    public void setUp() {
        temperature = 5.0;
        boilingPoint = 10.0;
        freezingPoint = 0.0;
        insignificantFluctuation = 0.5;

        t = new Thermometer(temperature, boilingPoint, freezingPoint, insignificantFluctuation);
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
    void shouldGetValidFreezingPoint() {
        assertEquals(freezingPoint, t.getFreezingPoint());
    }

    @Test
    void shouldGetValidInsignificantFluctuation() {
        assertEquals(insignificantFluctuation, t.getInsignificantFluctuation());
    }


    @Test
    void shouldSetValidBoilingPoint() {
        final double newBoilingPiont = 11.0;
        t.setBoilingPoint(newBoilingPiont);

        assertEquals(newBoilingPiont, t.getBoilingPoint());
    }


    @Test
    void shouldSetValidFreezingPoint() {
        final double newFreezingPoint = 11.0;
        t.setFreezingPoint(newFreezingPoint);

        assertEquals(newFreezingPoint, t.getFreezingPoint());
    }


    @Test
    void shouldSetValidInsignificantFluctuation() {
        final double newInsignificantFluctuation = 11.0;
        t.setInsignificantFluctuation(newInsignificantFluctuation);

        assertEquals(newInsignificantFluctuation, t.getInsignificantFluctuation());
    }

    @Test
    void shouldGetValidThermometerProperties() {
        Map<String, Double> thermometerProperties = t.getProperties();

        assertEquals(0.0, thermometerProperties.get(Thermometer.PREV_TEMPERATURE_KEY));
        assertEquals(temperature, thermometerProperties.get(Thermometer.TEMPERATURE_KEY));
        assertEquals(boilingPoint, thermometerProperties.get(Thermometer.BOILING_POINT_KEY));
        assertEquals(freezingPoint, thermometerProperties.get(Thermometer.FREEZING_POINT_KEY));
        assertEquals(insignificantFluctuation, thermometerProperties.get(Thermometer.INSIGNIFICANT_FLUCTUATION_KEY));
    }

    @Test
    void shouldSetNewObserver() {
        BoilingPointObserver bpo = new BoilingPointObserver(t);

        assertEquals(1, t.getObserverCount());
    }

    @Test
    void shouldSetNewObservers() {
        BoilingPointObserver bpo = new BoilingPointObserver(t);
        FreezingPointObserver fpo = new FreezingPointObserver(t);

        assertEquals(2, t.getObserverCount());
    }

    @Test
    void shouldNotifyObservers() {
        BoilingPointObserver bpo = new BoilingPointObserver(t);
        FreezingPointObserver fpo = new FreezingPointObserver(t);

        assertEquals(2, t.getObserverCount());
    }

    @Test
    void shouldOutputCorrectToString() {
        String template = "\n========== Thermometer Data ==========\n" +
                "Temperature                = %.2f C\n\n" +
                "Boiling Point              = %.2f C\n" +
                "Freezing Point             = %.2f C\n" +
                "Insignificant Fluctuation  = %.2f C\n" +
                "======================================";

        template = String.format(template,
                this.temperature,
                this.boilingPoint,
                this.freezingPoint,
                this.insignificantFluctuation
        );

        assertEquals(template, t.toString());
    }
}