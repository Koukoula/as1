package c301.koukoula_fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class editLogActivity extends AppCompatActivity {
    //Activity edits the previously selected logEntry.
     private static final String FILENAME = "file.sav";

        /** Called when the activity is first created. */
        private ArrayList<logEntry> logs = new ArrayList<logEntry>();
        private logEntry editedLog;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_log);

            Button submitButton = (Button) findViewById(R.id.submit);
            Button backButton = (Button) findViewById(R.id.back);
            loadFromFile();

            //Get the entry to edit.
            editedLog = logs.get(FuelTrackActivity.editPos);

            //Fills in text fields with current data.
            ((EditText) findViewById(R.id.date_edit)).setText(editedLog.getDate());
            ((EditText) findViewById(R.id.station_edit)).setText(editedLog.getStation());
            ((EditText) findViewById(R.id.odometer_edit)).setText(editedLog.getOdometer().toString());
            ((EditText) findViewById(R.id.grad_edit)).setText(editedLog.getFuelGrade());
            ((EditText) findViewById(R.id.amount_edit)).setText(editedLog.getFuelAmount().toString());
            ((EditText) findViewById(R.id.cost_edit)).setText(editedLog.getUnitCost().toString());

            submitButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    try {
                        setResult(RESULT_OK);
                        //Checks to make sure all fields are valid.
                        String date = ((EditText) findViewById(R.id.date_edit)).getText().toString();
                        String station = ((EditText) findViewById(R.id.station_edit)).getText().toString();
                        Double odometer = Double.parseDouble(((EditText) findViewById(R.id.odometer_edit)).getText().toString());
                        String grade = ((EditText) findViewById(R.id.grad_edit)).getText().toString();
                        Double amount = Double.parseDouble(((EditText) findViewById(R.id.amount_edit)).getText().toString());
                        Double cost = Double.parseDouble(((EditText) findViewById(R.id.cost_edit)).getText().toString());

                        //Tell users that they have to fill in certain data.
                        if (date.equals("")) {
                            Toast.makeText(editLogActivity.this, "Enter a date, please.", Toast.LENGTH_SHORT).show();
                        } else if (station.equals("")) {
                            Toast.makeText(editLogActivity.this, "Enter a station, please.", Toast.LENGTH_SHORT).show();
                        } else if (grade.equals("")) {
                            Toast.makeText(editLogActivity.this, "Enter a fuel grade, please.", Toast.LENGTH_SHORT).show();
                        } else {
                            //Everything is fine, commit changes.
                            editedLog.setDate(date);
                            editedLog.setStation(station);
                            editedLog.setOdometer(odometer);
                            editedLog.setFuelGrade(grade);
                            editedLog.setFuelAmount(amount);
                            editedLog.setUnitCost(cost);
                            editedLog.calcFuelCost();
                            saveInFile();
                            finish();
                        }
                    } catch (NumberFormatException e) {
                        //Error catch in case numbers aren't provided. Since I'm lazy, this is a catch all for all number values.
                        Toast.makeText(editLogActivity.this, "Enter all data, please.", Toast.LENGTH_SHORT).show();
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
        //Load and save outright taken from LonelyTwitter
        private void loadFromFile() {
            try {
                FileInputStream fis = openFileInput(FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                Gson gson = new Gson();

                // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
                Type listType = new TypeToken<ArrayList<logEntry>>() {}.getType();
                logs = gson.fromJson(in, listType);

            } catch (FileNotFoundException e) {
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
