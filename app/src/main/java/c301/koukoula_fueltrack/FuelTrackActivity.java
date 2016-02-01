package c301.koukoula_fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FuelTrackActivity extends AppCompatActivity {
    //The main Activity of the program. It allows users to view all logs.
    //click add to create a new log, exit to exit the program, and clear to erase all logs.
    //Click on a log entry to edit it.
    private static final String FILENAME = "file.sav";
    private ListView oldLogList;

    /** Called when the activity is first created. */
    private ArrayList<logEntry> logs = new ArrayList<logEntry>();
    private ArrayAdapter<logEntry> adapter;
    private double totalCost;

    //To edit a log we must, gasp, use a global variable that contains its index number.
    public static int editPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_track);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button addButton = (Button) findViewById(R.id.add);
        Button exitButton = (Button) findViewById(R.id.exit);
        Button clearButton = (Button) findViewById(R.id.clear);

        oldLogList = (ListView) findViewById(R.id.oldLogList);

        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                //Go to the addLogActivity to create a new log.
                startActivity(new Intent(FuelTrackActivity.this, addLogActivity.class));
            }

        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            //Exits the program
            @Override
            public void onClick(View v) {
              finish();
          }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Clears all logs.
                logs.clear();
                totalCost = 0;
                TextView totalCostView = (TextView) findViewById(R.id.totalCost);
                totalCostView.setText(" $0.00" );
                adapter.notifyDataSetChanged();
                saveInFile();
            }
        });


        oldLogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //http://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
            //User wishes to edit a log.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editPos = position;
                Intent intent = new Intent(FuelTrackActivity.this, editLogActivity.class);
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<logEntry>(this,
                                                    R.layout.list_item, logs);

        oldLogList.setAdapter(adapter);
        totalCost = 0.00;
        for (int i=0; i< logs.size(); i++) {
            totalCost = totalCost + logs.get(i).getFuelCost();
        }
        TextView totalCostView = (TextView) findViewById(R.id.totalCost);
        //A bit of redundency, but I had an error that could be fixed by stapling this here again.
        DecimalFormat twoDec = new DecimalFormat("#.##");
        totalCostView.setText(" $" + twoDec.format(totalCost));
    }

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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
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
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
