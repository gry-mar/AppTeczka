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

import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.utilities.Tree;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firestore.v1.WriteResult;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PlannerController extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListView listview;
    private final ArrayList<DrugDosaged> drugsDosaged = new ArrayList<>();
    private PlannerCustomAdapter plannerCustomAdapter;
    private Map<String, Object> data = new HashMap<>();
    private ArrayList<CustomListElement> customElements = new ArrayList<>();
    private int position = 0;

    private String emailUser;

    private FirebaseAuth mAuth;

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            position = intent.getIntExtra("switchPositions", position);

            Map<String, Object> data = new HashMap<>();
            customElements.get(position).setChecked(true);
            data.put(String.valueOf(position), customElements.get(position));

            db.collection(String.valueOf(emailUser)).document("lekiPlanner").set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                        Toast.makeText(PlannerController.this,"Zaaktualizowano switch",Toast.LENGTH_SHORT).show();
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        emailUser = currentUser.getEmail();

        System.out.println("gówno" + emailUser);

        if(currentUser == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_planner);

        listview = findViewById(R.id.listView1);

        getData();


    }

    public void getData(){
        DocumentReference docRef = db.collection(String.valueOf(emailUser))
                .document("lekiNaDzien");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> drugDosagedList = document.getData();
                        Map<String, Object> dDListSorted = new TreeMap<>(drugDosagedList);
                        for (Map.Entry<String, Object> entry : dDListSorted.entrySet()) {
                            Object race = entry.getValue();
                            Gson gson = new Gson();
                                DrugDosaged drugDosaged = gson.fromJson(String.valueOf(race), DrugDosaged.class);

                                drugsDosaged.add(drugDosaged);

                                calculateTime();
                            }
                    }
                    } else {
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


    public void calculateTime() {
        int n = 0;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        for (int i = 0; i < drugsDosaged.size(); i++) {

            Date current = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa z");
            String currentDate = simpleDateFormat.format(current.getTime()).substring(0, 10);

            if (drugsDosaged.get(i).getEndDate().equals(currentDate)) {
                // ZAKONCZONE DAWKOWANIE
            } else {
                int godz = 14 / Integer.parseInt(drugsDosaged.get(i).getDosagesPerDay());
                int godzTemp = 0;
                ArrayList<String> dosagesTimes = new ArrayList<>();
                for (int j = 0; j < Integer.parseInt(drugsDosaged.get(i).getDosagesPerDay()); j++) {
                    dosagesTimes.add((8 + godzTemp) + ":" + "00");

                    godzTemp += godz;


                    customElements.add(new CustomListElement
                            (drugsDosaged.get(i).getName(), dosagesTimes.get(j), false));

                    data.put(String.valueOf(n), new CustomListElement
                            (drugsDosaged.get(i).getName(), dosagesTimes.get(j), false));
                    n++;


                }
            }
        }
        System.out.println("before" + data);
        Map<String, Object> dataSorted = new TreeMap<String,Object>(data);
        System.out.println("after" + dataSorted);
        db.collection(String.valueOf(emailUser)).document("lekiPlanner")
                .set(dataSorted).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(PlannerController.this, "udalo sie",
                            Toast.LENGTH_SHORT).show();
                System.out.println("CO FINALNIE WCHODZI DO BAZY: " + data);
                if (task.isComplete())
                    toggleButtonsGet();
            }
        });
    }

        private void toggleButtonsGet() {
            DocumentReference docRef = db.collection(String.valueOf(emailUser))
                    .document("togglebuttons");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @SuppressLint("NewApi")
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Map<String, Object> toggleButtons = document.getData();
                            Map<String, Object> tbSorted = new TreeMap<String,Object>(toggleButtons);
                            for (Map.Entry<String, Object> entry : tbSorted.entrySet()) {
                                Object buttonValue = entry.getValue();

                                boolean btnValue = Boolean.parseBoolean(String.valueOf(buttonValue));
                                int position = Integer.parseInt(entry.getKey());

                                //System.out.println("Przy odbieraniu z togglebuttons z firebase: " + customElements);
                                data.put(String.valueOf(position), new CustomListElement(customElements.get(position).getName(),
                                        customElements.get(position).getTime(), false));
                                db.collection(String.valueOf(emailUser))
                                        .document("lekiPlanner")
                                        .update(String.valueOf(position), new CustomListElement(customElements.get(position).getName(),
                                                customElements.get(position).getTime(), btnValue));

                            }
                        }
                    } else {
                    }
                    if(task.isComplete())
                        setDrugsPlanner();
                }
            });
        }

        private void setDrugsPlanner() {
            customElements.clear();


            DocumentReference docRef2 = db.collection(String.valueOf(emailUser)).document("lekiPlanner");
            docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @SuppressLint("NewApi")
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Map<String, Object> drugPlannerList = document.getData();
                            Map<String, Object> dPListSorted = new TreeMap<>(drugPlannerList);
                            for (Map.Entry<String, Object> entry : dPListSorted.entrySet()) {
                                Object planner = entry.getValue();
                                Gson gson = new Gson();
                                String planner2 = planner.toString().replace(":", "");
                                CustomListElement customListElement =
                                        gson.fromJson(String.valueOf(planner2), CustomListElement.class);
                                customListElement.setTime(customListElement.getTime().replace("00", ":00"));
                                customElements.add(customListElement);
                                System.out.println(customListElement);
                            }
                        }
                    } else {
                    }
                    if(task.isComplete())
                        setAdapter();
                }
            });
        }

        private void setAdapter(){
        plannerCustomAdapter = new PlannerCustomAdapter(customElements, PlannerController.this);

        listview.setAdapter(plannerCustomAdapter);
    }




    public void btnReturnOnClick(View view) {
        Intent intent = new Intent(this, MenuAll.class);
        startActivity(intent);
        finish();
    }

}

