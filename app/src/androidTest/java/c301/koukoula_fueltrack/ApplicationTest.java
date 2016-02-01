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

    }
}
    /*
    public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
}
    */