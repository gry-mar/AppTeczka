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

    ListView listview;

    PlannerCustomAdapter plannerCustomAdapter;

    private boolean switch1 = false;
    private int position = 0;
    private ArrayList<CustomListElement> customElements = new ArrayList<>();

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch1 = intent.getBooleanExtra("switchBooleans", switch1);
            position = intent.getIntExtra("switchPositions", position);

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
        //ArrayList<String> drugNames = new ArrayList<>(Arrays.asList(drugNamesList));
        //ArrayList<String> drugTimes = new ArrayList<>(Arrays.asList(drugTimesList));
        //if (savedInstanceState == null) {
        //    for (int i = 0; i < drugNamesList.length; i++) {
        //        customElements.add(new CustomListElement(drugNames.get(i), drugTimes.get(i), switches[i]));
        //    }
        /*}else{
            System.out.println("coś tu jest");
            String jsonItems = savedInstanceState.getString(PREFS_NAME, null);
            Gson gson = new Gson();
            CustomListElement[] favoriteItems = gson.fromJson(jsonItems, CustomListElement[].class);
            customElements.addAll(Arrays.asList(favoriteItems));
        }
        plannerCustomAdapter = new PlannerCustomAdapter(customElements, PlannerController.this);

        listview.setAdapter(plannerCustomAdapter); */
    }



    public void btnReturnOnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

