package edu.ib.appteczkaandroid;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlannerController extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "";
    private ListView listview;
    private final ArrayList<DrugDosaged> drugsDosaged = new ArrayList<>();
    private PlannerCustomAdapter plannerCustomAdapter;
    private final ArrayList<String> dosagesPerDay = new ArrayList<>();
    private final ArrayList<String> dosageEndDate = new ArrayList<>();
    private boolean switch1 = false;
    private int position = 0;

    private final ArrayList<CustomListElement> customElements = new ArrayList<>();

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch1 = intent.getBooleanExtra("switchBooleans", switch1);
            position = intent.getIntExtra("switchPositions", position);

            Toast.makeText(PlannerController.this, switch1 + "  " + position, Toast.LENGTH_SHORT).show();
            if(customElements != null)
                customElements.get(position).setChecked(true);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> data = new HashMap<>();

            customElements.get(position).setChecked(true);
            data.put(String.valueOf(position),customElements.get(position));


            db.collection("useremail@gmail.com").document("lekiPlanner").set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                        Toast.makeText(PlannerController.this,"Zaaktualizowano switch",Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        listview = findViewById(R.id.listView1);
        //getData();
        DocumentReference docRef = db.collection("useremail@gmail.com").document("lekiPlanner");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> drugPlannerList = document.getData();
                        assert drugPlannerList != null;
                        if(drugPlannerList.isEmpty()){
                            getData();
                        }
                        for (Map.Entry<String, Object> entry : drugPlannerList.entrySet()) {
                            Object planner = entry.getValue();
                            Gson gson = new Gson();
                            String formPlanner = planner.toString().replace(":","");
                            System.out.println(formPlanner);
                            CustomListElement customListElement = gson.fromJson(formPlanner, CustomListElement.class);
                            customListElement.setTime(customListElement.getTime().replace("00", ":00"));
                            customElements.add(customListElement);

                        }
                    }
                } else {
                    Log.d(TAG, "No such document");
                }
                plannerCustomAdapter = new PlannerCustomAdapter(customElements, PlannerController.this);

                listview.setAdapter(plannerCustomAdapter);
            }
        });

    }

    public void getData(){
        DocumentReference docRef = db.collection("useremail@gmail.com").document("lekiNaDzien");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> drugDosagedList = document.getData();
                        for (Map.Entry<String, Object> entry : drugDosagedList.entrySet()) {
                            Object race = entry.getValue();
                            Gson gson = new Gson();
                            DrugDosaged drugDosaged = gson.fromJson(String.valueOf(race), DrugDosaged.class);
                            drugsDosaged.add(drugDosaged);

                        }
                        calculateTime();
                    }

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        int lastTimeStarted = settings.getInt("last_time_started", -1);
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_YEAR);

        if (today != lastTimeStarted) {
            // = null;

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("last_time_started", today);
            editor.commit();
        }
    }


    public void calculateTime(){
        System.out.println(drugsDosaged);
        int n = 0;
        for(int i = 0; i <drugsDosaged.size(); i++){
            dosagesPerDay.add(drugsDosaged.get(i).getDosagesPerDay());
            dosageEndDate.add(drugsDosaged.get(i).getEndDate());
            Date current = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa z");
            String currentDate = simpleDateFormat.format(current.getTime()).substring(0,10);

                if(drugsDosaged.get(i).getEndDate().equals(currentDate)){
                    // ZAKONCZONE DAWKOWANIE
                }else{
                    int godz = 14/Integer.parseInt(drugsDosaged.get(i).getDosagesPerDay());
                    int godzTemp=0;
                    ArrayList<String> dosagesTimes = new ArrayList<>();
                    for(int j = 0; j<Integer.parseInt(drugsDosaged.get(i).getDosagesPerDay()); j++) {
                        dosagesTimes.add((8 + godzTemp) + ":" + "00");

                        godzTemp += godz;

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> data = new HashMap<>();

                        //if (customElements == null) {
                            data.put(String.valueOf(n), new CustomListElement
                                    (drugsDosaged.get(i).getName(), dosagesTimes.get(j), false));
                            n++;
                            customElements.add(new CustomListElement
                                    (drugsDosaged.get(i).getName(), dosagesTimes.get(j), false));

                            db.collection("useremail@gmail.com").document("lekiPlanner").set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        Toast.makeText(PlannerController.this, "udalo sie", Toast.LENGTH_SHORT).show();
                                }
                            });
                            plannerCustomAdapter = new PlannerCustomAdapter(customElements, PlannerController.this);

                            listview.setAdapter(plannerCustomAdapter);
                        }
                    //}
                    }
                    }
            }




    public void btnReturnOnClick(View view) {
        Intent intent = new Intent(this, MenuAll.class);
        startActivity(intent);
        finish();
    }

}

