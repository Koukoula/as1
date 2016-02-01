package c301.koukoula_fueltrack;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by unkno on 2016-02-01.
 */
public class logEntryTest extends ActivityInstrumentationTestCase2 {
    public logEntryTest() {
        super(FuelTrackActivity.class);
    }

    public void testCalcFuelCost() {
        logEntry log1 = new logEntry();
        log1.setUnitCost(50.0);
        log1.setFuelAmount(4.0);
        log1.calcFuelCost();
        assertEquals(2.0, log1.getFuelCost());
    }
}