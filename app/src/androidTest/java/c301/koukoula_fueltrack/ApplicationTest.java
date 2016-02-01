package c301.koukoula_fueltrack;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2 {
    public ApplicationTest(){
        super(FuelTrackActivity.class);
    }

    public void testClear(){
        ArrayList<logEntry> logs = new ArrayList<logEntry>();
        logEntry log = new logEntry();
        logs.add(log);
        logs.clear();
        assertFalse(logs.contains(log));
    }
    public void testSum(){
        ArrayList<logEntry> logs = new ArrayList<logEntry>();
        double totalCost = 0.00;
        logEntry log1 = new logEntry();
        log1.setUnitCost(50.0);
        log1.setFuelAmount(4.0);
        log1.calcFuelCost();
        logEntry log2 = new logEntry();
        log2.setUnitCost(25.0);
        log2.setFuelAmount(4.0);
        log2.calcFuelCost();
        logs.add(log1);
        logs.add(log2);
        for (int i=0; i< logs.size(); i++) {
            totalCost = totalCost + logs.get(i).getFuelCost();
        }
        assertEquals(3.0,totalCost);
    }
}
    /*
    public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
}
    */