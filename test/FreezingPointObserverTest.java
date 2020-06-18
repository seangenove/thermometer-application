import com.trenchdevs.FreezingPointObserver;
import com.trenchdevs.Thermometer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FreezingPointObserverTest {

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
    void Should_Notify_When_TemperatureIsAtFreezingPoint() {
        FreezingPointObserver fpo = new FreezingPointObserver(t);

        t.setTemperature(freezingPoint);

        assertTrue(fpo.getShouldNotify());
    }

    @Test
    void Should_NotifyOnce_WhenTemperatureExceedsFreezingPoint() {
        FreezingPointObserver fpo = new FreezingPointObserver(t);

        t.setTemperature(freezingPoint - insignificantFluctuation);
        assertTrue(fpo.getShouldNotify());

        t.setTemperature(freezingPoint);
        assertFalse(fpo.getShouldNotify());
    }

    @Test
    void Should_NotifyOnce_When_TemperatureIsAlreadyAtFreezingPoint() {
        FreezingPointObserver fpo = new FreezingPointObserver(t);

        t.setTemperature(freezingPoint);
        assertTrue(fpo.getShouldNotify());

        t.setTemperature(freezingPoint - 1.0);
        assertFalse(fpo.getShouldNotify());
    }

    @Test
    void Should_NotifyOnce_When_TemperatureFluctuatesTwice() {
        FreezingPointObserver fpo = new FreezingPointObserver(t);

        t.setTemperature(freezingPoint);
        assertTrue(fpo.getShouldNotify());

        t.setTemperature(freezingPoint - insignificantFluctuation);
        assertFalse(fpo.getShouldNotify());

        t.setTemperature(freezingPoint);
        assertFalse(fpo.getShouldNotify());

        t.setTemperature(freezingPoint + insignificantFluctuation);
        assertFalse(fpo.getShouldNotify());
    }

    @Test
    void Should_NotifyOnce_WhenTemperatureFluctuatesByPointOne() {
        FreezingPointObserver fpo = new FreezingPointObserver(t);

        t.setTemperature(freezingPoint + 0.1);
        assertFalse(fpo.getShouldNotify());

        t.setTemperature(freezingPoint);
        assertTrue(fpo.getShouldNotify());

        t.setTemperature(freezingPoint + 0.1);
        assertFalse(fpo.getShouldNotify());

        t.setTemperature(freezingPoint);
        assertFalse(fpo.getShouldNotify());

        t.setTemperature(freezingPoint - 1.0);
        assertFalse(fpo.getShouldNotify());
    }

    @Test
    void Should_NotifyTwice_When_TempExceedsFreezingPoint_AndWhen_TempResetsAndIsSetAtFreezingPoint() {
        FreezingPointObserver fpo = new FreezingPointObserver(t);

        t.setTemperature(freezingPoint - insignificantFluctuation);
        assertTrue(fpo.getShouldNotify());

        t.setTemperature(freezingPoint + insignificantFluctuation);
        assertFalse(fpo.getShouldNotify());

        t.setTemperature(freezingPoint);
        assertTrue(fpo.getShouldNotify());
    }

    @Test
    void Should_NotNotify_When_TemperatureIsNotAtFreezingPoint() {
        FreezingPointObserver fpo = new FreezingPointObserver(t);

        assertFalse(fpo.getShouldNotify());
    }

    @Test
    void Should_NotNotify_When_TemperatureHasInsignificantFluctuation() {
        FreezingPointObserver fpo = new FreezingPointObserver(t);

        t.setTemperature(freezingPoint);
        assertTrue(fpo.getShouldNotify());

        t.setTemperature(freezingPoint + insignificantFluctuation);
        assertFalse(fpo.getShouldNotify());

        t.setTemperature(freezingPoint);
        assertFalse(fpo.getShouldNotify());

        t.setTemperature(freezingPoint - insignificantFluctuation);
        assertFalse(fpo.getShouldNotify());
    }

    @Test
    void Should_Notify_When_FreezingPointIsChangedToCurrentTemperature() {
        FreezingPointObserver fpo = new FreezingPointObserver(t);

        t.setFreezingPoint(temperature);

        assertTrue(fpo.getShouldNotify());
    }

}