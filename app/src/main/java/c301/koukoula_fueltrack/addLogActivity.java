package c301.koukoula_fueltrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class addLogActivity extends AppCompatActivity {
    //This activity is used to create a new log.
    private static final String FILENAME = "file.sav";
    private ArrayList<logEntry> logs = new ArrayList<logEntry>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);

        Button submitButton = (Button) findViewById(R.id.submit);
        Button backButton = (Button) findViewById(R.id.back);

        submitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //The submit button creates the log. You must fill in all fields.
                try {
                    setResult(RESULT_OK);
                    //Go through each text field and make sure all constraints are filled.
                    String date = ((EditText) findViewById(R.id.date_entered)).getText().toString();
                    String station = ((EditText) findViewById(R.id.station_entered)).getText().toString();
                    Double odometer = Double.parseDouble(((EditText) findViewById(R.id.odometer_entered)).getText().toString());
                    String grade = ((EditText) findViewById(R.id.grad_entered)).getText().toString();
                    Double amount = Double.parseDouble(((EditText) findViewById(R.id.amount_entered)).getText().toString());
                    Double cost = Double.parseDouble(((EditText) findViewById(R.id.cost_entered)).getText().toString());

                    //Checks to make sure all string fields are filled in.
                    if (date.equals("")) {
                        Toast.makeText(addLogActivity.this, "Enter a date, please.", Toast.LENGTH_SHORT).show();
                    } else if (station.equals("")) {
                        Toast.makeText(addLogActivity.this, "Enter a station, please.", Toast.LENGTH_SHORT).show();
                    } else if (grade.equals("")) {
                        Toast.makeText(addLogActivity.this, "Enter a fuel grade, please.", Toast.LENGTH_SHORT).show();
                    } else {
                        //No invalid fields, can commit.
                        logEntry latestLog = new logEntry();
                        latestLog.setDate(date);
                        latestLog.setStation(station);
                        latestLog.setOdometer(odometer);
                        latestLog.setFuelGrade(grade);
                        latestLog.setFuelAmount(amount);
                        latestLog.setUnitCost(cost);
                        latestLog.calcFuelCost();
                        loadFromFile();
                        logs.add(latestLog);
                        saveInFile();
                        finish();
                    }
                } catch (NumberFormatException e) {
                    //Error catch in case numbers aren't provided. Since I'm lazy, this is a catch all for all number values.
                    Toast.makeText(addLogActivity.this, "Enter all data, please.", Toast.LENGTH_SHORT).show();
                }
            }

        });
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Nothings changed, go back to FuelTrackActivity
                finish();
            }
        });
    }
    //load and Save taken from lonely twitter.
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<logEntry>>() {}.getType();
            logs = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logs = new ArrayList<logEntry>();
        }
    }
        private void saveInFile() {
            try {
                FileOutputStream fos = openFileOutput(FILENAME, 0);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
                Gson gson = new Gson();
                gson.toJson(logs, out);
                out.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

}
