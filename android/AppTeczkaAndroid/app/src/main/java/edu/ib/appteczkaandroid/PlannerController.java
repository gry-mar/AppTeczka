package edu.ib.appteczkaandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class PlannerController extends AppCompatActivity {

    private static final String PREFS_NAME = "elements";
    ListView listview;

    String[] drugNamesList = new String[]{
            "Apap",
            "Desmoxan",
            "Nimesil",
            "Inny lek",
            "Inny lek 2",
            "Inny lek 3",
            "Inny lek 4",
            "Inny lek 5",
            "Inny lek 6",
    };

    String[] drugTimesList = new String[]{
            "12:00",
            "13:00",
            "13:33",
            "9:00",
            "8:30",
            "9:50",
            "10:10",
            "11:10",
            "22:00",
    };
    PlannerCustomAdapter plannerCustomAdapter;
    private boolean switch1 = false;
    private int position = 0;
    private ArrayList<CustomListElement> customElements = new ArrayList<>();
    private boolean[] switches = new boolean[drugNamesList.length];
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch1 = intent.getBooleanExtra("switchBooleans", switch1);
            position = intent.getIntExtra("switchPositions", position);
            switches[position] = switch1;

            Toast.makeText(PlannerController.this, switch1 + "  " + position, Toast.LENGTH_SHORT).show();
            if(customElements != null)
                customElements.get(position).setChecked(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(savedInstanceState);
        setContentView(R.layout.activity_planner);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        listview = findViewById(R.id.listView1);
        ArrayList<String> drugNames = new ArrayList<>(Arrays.asList(drugNamesList));
        ArrayList<String> drugTimes = new ArrayList<>(Arrays.asList(drugTimesList));
        if (savedInstanceState == null) {
            for (int i = 0; i < drugNamesList.length; i++) {
                customElements.add(new CustomListElement(drugNames.get(i), drugTimes.get(i), switches[i]));
            }
        }else{
            System.out.println("coś tu jest");
            String jsonItems = savedInstanceState.getString(PREFS_NAME, null);
            Gson gson = new Gson();
            CustomListElement[] favoriteItems = gson.fromJson(jsonItems, CustomListElement[].class);
            customElements.addAll(Arrays.asList(favoriteItems));
        }
        plannerCustomAdapter = new PlannerCustomAdapter(customElements, PlannerController.this);

        listview.setAdapter(plannerCustomAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        /*SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String ItemsJson = gson.toJson(customElements);
        editor.putString(PREFS_NAME, ItemsJson);
        editor.apply();*/
        Gson gson = new Gson();
        String ItemsJson = gson.toJson(customElements);
        System.out.println(customElements);
        System.out.println(Arrays.toString(switches));
        outState.putString(PREFS_NAME, ItemsJson);
        System.out.println(ItemsJson);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        System.out.println("LalalalaRESTORE");
        String jsonItems = savedInstanceState.getString(PREFS_NAME, null);
        Gson gson = new Gson();
        CustomListElement[] favoriteItems = gson.fromJson(jsonItems, CustomListElement[].class);
        customElements.addAll(Arrays.asList(favoriteItems));
        System.out.println(customElements);
        plannerCustomAdapter = new PlannerCustomAdapter(customElements, PlannerController.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        System.out.println("LalalalaRESUME");
        System.out.println(Arrays.toString(switches));
        if (sharedPref.contains(PREFS_NAME)) {
            String jsonItems = sharedPref.getString(PREFS_NAME, null);
            Gson gson = new Gson();
            CustomListElement[] favoriteItems = gson.fromJson(jsonItems, CustomListElement[].class);
            customElements.addAll(Arrays.asList(favoriteItems));
            ItemsFromSharedPrefs = new ArrayList<>(customElements);
            System.out.println(ItemsFromSharedPrefs);
            plannerCustomAdapter = new PlannerCustomAdapter(ItemsFromSharedPrefs, PlannerController.this);
        } */

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        System.out.println(customElements);
        System.out.println(Arrays.toString(switches));
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        System.out.println("LalalalaSTART");
        if (sharedPref.contains(PREFS_NAME)) {
            String jsonItems = sharedPref.getString(PREFS_NAME, null);
            Gson gson = new Gson();
            CustomListElement[] favoriteItems = gson.fromJson(jsonItems, CustomListElement[].class);
            customElements.addAll(Arrays.asList(favoriteItems));
            ItemsFromSharedPrefs = new ArrayList<>(customElements);
            System.out.println(ItemsFromSharedPrefs);
            plannerCustomAdapter = new PlannerCustomAdapter(ItemsFromSharedPrefs, PlannerController.this);


        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*
        System.out.println(customElements);
        System.out.println(Arrays.toString(switches));
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String ItemsJson = gson.toJson(customElements);
        editor.putString(PREFS_NAME, ItemsJson);
        editor.apply();
        System.out.println("LalalalaSTOP");*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("LalalalaDESTROY");

    }

    public void btnReturnOnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

