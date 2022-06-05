package edu.ib.appteczkaandroid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import edu.ib.appteczkaandroid.databinding.ActivityMenuAllBinding;

public class MenuAll extends AppCompatActivity {

    private Button btnAllDrugs, btnDailyDrugs, btnInteractions;

    private FirebaseAuth mAuth;

    private String emailUser;

    private ArrayList<DrugInAll> drugsInAll = new ArrayList<>();
    private ArrayList<Date> datesList = new ArrayList<>();

    final Calendar calendar = Calendar.getInstance();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        emailUser = currentUser.getEmail();

        //createNotificationChannel();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(String.valueOf(emailUser)).document("lekiWszystkie")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> drugInAllList = document.getData();
                        Map<String, Object> dAListSorted = new TreeMap<>(drugInAllList);
                        for (Map.Entry<String, Object> entry : dAListSorted.entrySet()) {
                            Object race = entry.getValue();
                            Gson gson = new Gson();
                            DrugInAll drugInAll= gson.fromJson(String.valueOf(race), DrugInAll.class);

                            drugsInAll.add(drugInAll);
                        }
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        for(DrugInAll d:drugsInAll){
                            try {
                                Date date = format.parse(d.getDate());
                                datesList.add(date);
                            } catch (ParseException e) {
                                System.out.println("błąd parsowania daty");
                            }
                        }


                    }
                } else {
                }}});
        if(datesList.size() !=0){
            Date minDate = Collections.min(datesList);
            calendar.set(Calendar.YEAR, minDate.getYear());
            calendar.set(Calendar.MONTH,minDate.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH,minDate.getDay());
            calendar.set(Calendar.HOUR_OF_DAY,15);
            calendar.set(Calendar.MINUTE,30);
            Intent intent = new Intent(getApplicationContext(),NotifyIfExpDate.class);
            intent.putExtra("titleExtra","Wyrzuć lek");
            intent.putExtra("messageExtra","Kończy się data ważności jednego z twoich leków");
            intent.putExtra("reqCode",100);
            sendBroadcast(intent);

        }


        if(currentUser == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_menu_all);

        btnAllDrugs = findViewById(R.id.btnAllDrugs);
        btnDailyDrugs = findViewById(R.id.btnDailyDrugs);
        btnInteractions = findViewById(R.id.btnInteractions);
        btnAllDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AllDrugs.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnDailyDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlannerController.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        btnInteractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InteractionsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }



    public void onNoteClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), SetReminderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onLogoutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
/*
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(){
        CharSequence name = "Default channel";
        String desc = "Opis jakis";
        String channelID = "default";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelID,name, importance);
        channel.setDescription(desc);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    } */
}