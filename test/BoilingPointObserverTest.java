import com.trenchdevs.BoilingPointObserver;
import com.trenchdevs.FreezingPointObserver;
import com.trenchdevs.Thermometer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoilingPointObserverTest {

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
    void Should_Notify_When_TemperatureIsAtBoilingPoint() {
        BoilingPointObserver bpo = new BoilingPointObserver(t);

        t.setTemperature(boilingPoint);

        assertTrue(bpo.getShouldNotify());
    }

    @Test
    void Should_NotNotify_When_TemperatureIsNotAtBoilingPoint() {
        BoilingPointObserver bpo = new BoilingPointObserver(t);

        assertFalse(bpo.getShouldNotify());
    }

    @Test
    void Should_NotNotify_When_TemperatureIsSetBelowBoilingPoint() {
        BoilingPointObserver bpo = new BoilingPointObserver(t);

        t.setTemperature(temperature + 1.0);

        assertFalse(bpo.getShouldNotify());
    }

    @Test
    void Should_NotNotify_When_TemperatureHasInsignificantFluctuation() {
        BoilingPointObserver bpo = new BoilingPointObserver(t);

        t.setTemperature(boilingPoint);
        assertTrue(bpo.getShouldNotify());

        t.setTemperature(boilingPoint + insignificantFluctuation);
        assertFalse(bpo.getShouldNotify());
    }

    @Test
    void Should_Notify_When_BoilingPointIsChangedToCurrentTemperature() {
        BoilingPointObserver bpo = new BoilingPointObserver(t);

        t.setBoilingPoint(temperature);

        assertTrue(bpo.getShouldNotify());
    }

    @Test
    void Should_NotifyOnce_When_TemperatureIsAlreadytBoilingPoint() {
        BoilingPointObserver bpo = new BoilingPointObserver(t);

        t.setTemperature(boilingPoint);
        assertTrue(bpo.getShouldNotify());

        t.setTemperature(boilingPoint + 1.0);
        assertFalse(bpo.getShouldNotify());
    }

    @Test
    void Should_NotifyOnce_When_TemperatureFluctuatesTwice() {
        BoilingPointObserver bpo = new BoilingPointObserver(t);

        t.setTemperature(9.50);
        assertFalse(bpo.getShouldNotify());

        t.setTemperature(10);
        assertTrue(bpo.getShouldNotify());

        t.setTemperature(9.50);
        assertFalse(bpo.getShouldNotify());

        t.setTemperature(10);
        assertFalse(bpo.getShouldNotify());
    }

    @Test
    void Should_NotifyOnce_WhenTemperatureExceedsBoilingPoint() {
        BoilingPointObserver bpo = new BoilingPointObserver(t);

        t.setTemperature(11.0);
        assertTrue(bpo.getShouldNotify());

        t.setTemperature(10);
        assertFalse(bpo.getShouldNotify());
    }


}