package edu.ib.appteczkaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PlannerController extends AppCompatActivity {

    ListView listview;

    String[] drugNamesList = new String[] {
            "Apap"
    };

    String[] drugTimesList = new String[] {
            "12:00"
    };

    PlannerCustomAdapter plannerCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_planner);

        listview = findViewById(R.id.listView1);
        ArrayList<String> drugNames = new ArrayList<>(Arrays.asList(drugNamesList));
        ArrayList<String> drugTimes = new ArrayList<>(Arrays.asList(drugTimesList));

        plannerCustomAdapter = new PlannerCustomAdapter(drugNames,drugTimes, PlannerController.this);
        listview.setAdapter(plannerCustomAdapter);

    }

    public void btnReturnOnClick(View view) {
        finish();
    }



}