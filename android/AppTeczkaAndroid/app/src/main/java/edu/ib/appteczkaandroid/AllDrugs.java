package edu.ib.appteczkaandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class AllDrugs extends AppCompatActivity {

    //private ArrayList<DrugInAll> drugsList;

    private String drugName, drugExpDate;
    private int state, rowId, rowIdOld;
    private String chosenName, chosenDate, emailUser, keyId;
    private int drugId;

    String[] keys1;
    String idForDailyDrug;

    private FirebaseAuth mAuth;

    ArrayList<DrugInAll> drugsInAll = new ArrayList<>();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        emailUser = currentUser.getEmail();
        System.out.println(emailUser);

        if(currentUser == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_all_drugs);

        state = 0;

        Button btnRemoveDrug = findViewById(R.id.btnRemoveDrug);

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
                        keys1 = new String[drugInAllList.size()];
                        int i = 0;

                        for (Map.Entry<String, Object> entry : dAListSorted.entrySet()) {
                            Object race = entry.getValue();
                            System.out.println(race.toString());
                            Gson gson = new Gson();
                            DrugInAll drugInAll= gson.fromJson(String.valueOf(race), DrugInAll.class);

                            drugsInAll.add(drugInAll);
                            keys1[i] = entry.getKey();
                            i++;
                        }
                        System.out.println("Leki wszystkie:"+drugsInAll);
                        //String max = String.valueOf(drugsInAll.size());
                    }
                } else {
                    Toast.makeText(AllDrugs.this,"Wyst??pi?? b????d. Prosz?? spr??bowa?? ponownie.",Toast.LENGTH_SHORT).show();
                }

                        TableLayout table = (TableLayout) AllDrugs.this.findViewById(R.id.tableAllDrugs);
                        position = 0;

                            for(DrugInAll d:drugsInAll){
                                String key = d.getName();
                                String value = d.getDate();
                            final TableRow row = new TableRow(AllDrugs.this);

                            TextView tv1 = new TextView(AllDrugs.this);
                            tv1.setText(key);
                            tv1.setTextColor(Color.parseColor("#3e3e3e"));
                            tv1.setGravity(Gravity.CENTER);
                            tv1.setPadding(0, 10, 0, 10);
                            tv1.setHeight(90);
                            tv1.setMaxWidth(120);
                            tv1.setBackgroundResource(R.drawable.cell_shape);
                            tv1.setMinWidth(120);

                            row.addView(tv1);

                            TextView tv2 = new TextView(AllDrugs.this);
                            tv2.setText(value);
                            tv2.setTextColor(Color.parseColor("#3e3e3e"));
                            tv2.setGravity(Gravity.CENTER);
                            tv2.setPadding(0, 10, 0, 10);
                            tv2.setHeight(90);
                            tv2.setMaxWidth(80);
                            tv2.setMinWidth(80);
                            tv2.setBackgroundResource(R.drawable.cell_shape);

                            row.addView(tv2);

                            row.setId(position);
                            position++;
                            row.setClickable(true);
                            row.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (state == 0) {
                                        row.setBackgroundColor(Color.parseColor("#adadad"));
                                        tv1.setBackgroundResource(R.drawable.cell_shape_clicked1);
                                        tv2.setBackgroundResource(R.drawable.cell_shape_clicked1);
                                        state = 1;
                                        rowId = row.getId();
                                        rowIdOld = rowId;

                                        TextView name = (TextView) row.getChildAt(0);
                                        TextView date = (TextView) row.getChildAt(1);
                                        chosenName = name.getText().toString();
                                        chosenDate = date.getText().toString();
                                        System.out.println(chosenName);
                                        System.out.println(chosenDate);

                                    } else if (state == 1) {
                                        if (row.getId() == rowIdOld) {
                                            row.setBackgroundColor(Color.TRANSPARENT);
                                            tv1.setBackgroundResource(R.drawable.cell_shape_clicked2);
                                            tv2.setBackgroundResource(R.drawable.cell_shape_clicked2);
                                            state = 0;
                                        } else {
                                            Toast.makeText(AllDrugs.this, "Nie mo??na r??wnocze??nie zaznaczy?? " +
                                                    "wi??cej ni?? jednego leku.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });

                            table.addView(row);
                        }
                        Button startDosage = findViewById(R.id.btnStartDrug);
                        startDosage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if ((chosenDate != null) || (chosenName != null)) {
                                    Intent intent = new Intent(AllDrugs.this, DosageActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("name", chosenName);
                                    bundle.putString("date", chosenDate);
                                    bundle.putInt("id", rowId);

                                    keyId = keys1[rowId];

                                    bundle.putString("idKey", keyId);

                                    intent.putExtras(bundle);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    System.out.println(chosenName + chosenDate + keyId);
                                    System.out.println("EEEE " + String.valueOf(intent));
                                    finish();
                                } else {
                                    Toast.makeText(AllDrugs.this, "Aby przej???? do dawkowania, " +
                                            "najpierw zaznacz lek.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        table.requestLayout();
                    }


//                } else {
//                    Toast.makeText(AllDrugs.this,"Wyst??pi?? b????d. Prosz?? spr??bowa?? ponownie.",Toast.LENGTH_SHORT).show();
//                }
            //}
        });
        btnRemoveDrug.setOnClickListener(new View.OnClickListener() {
            String[] keys;
            @Override
            public void onClick(View view) {
                Map<String, Object> deleteDrug = new HashMap<>();
                Map<String, Object> deleteDrug2 = new HashMap<>();

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
                                keys = new String[drugInAllList.size()];
                                int i = 0;
                                for (Map.Entry<String, Object> entry : dAListSorted.entrySet()) {
                                    Object race = entry.getValue();
                                    Gson gson = new Gson();
                                    DrugInAll drugInAll = gson.fromJson(String.valueOf(race), DrugInAll.class);

                                    drugsInAll.add(drugInAll);
                                    keys[i] = entry.getKey();
                                    i++;

                                }
                                System.out.println("Leki wszystkie:" + drugsInAll);
                            }
                        } else {
                            Toast.makeText(AllDrugs.this, "Wyst??pi?? b????d. Prosz?? spr??bowa?? ponownie.", Toast.LENGTH_SHORT).show();
                        }
                        if(task.isComplete()){
                            db.collection(String.valueOf(emailUser)).document("lekiNaDzien")
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @SuppressLint("NewApi")
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        // Document found in the offline cache
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Map<String, Object> drugDailyList = document.getData();
                                            Map<String, Object> dAListSorted = new TreeMap<>(drugDailyList);
                                            //keys = new String[size()];
                                            //int i = 0;
                                            for (Map.Entry<String, Object> entry : dAListSorted.entrySet()) {
                                                Object race = entry.getValue();
                                                Gson gson = new Gson();
                                                DrugDosaged drugDos = gson.fromJson(String.valueOf(race), DrugDosaged.class);
                                                if(drugDos.getId().equals(keys[rowId])){
                                                    idForDailyDrug = entry.getKey();
                                                    System.out.println("ID WCZESNIEJ: " + idForDailyDrug);
                                                    System.out.println("Jaki to lek: " + drugDos.toString());
                                                }

                                                //drugsInAll.add(drugInAll);
                                                //keys[i] = entry.getKey();
                                                //i++;

                                            }
                                            //System.out.println("Leki wszystkie:" + drugsInAll);
                                        }
                                    } else {
                                       // Toast.makeText(AllDrugs.this, "Wyst??pi?? b????d. Prosz?? spr??bowa?? ponownie.", Toast.LENGTH_SHORT).show();
                                    }
                                    if(task.isComplete()){
                                        deleteDrug.put(String.valueOf(keys[rowId]), FieldValue.delete());
                                        FirebaseFirestore.getInstance()
                                                .collection(String.valueOf(emailUser))
                                                .document("lekiWszystkie")
                                                .update(deleteDrug);

                                        deleteDrug2.put(String.valueOf(idForDailyDrug), FieldValue.delete());
                                        FirebaseFirestore.getInstance()
                                                .collection(String.valueOf(emailUser))
                                                .document("lekiNaDzien")
                                                .update(deleteDrug2);

                                        //db.collection(String.valueOf(emailUser)).document("togglebuttons").delete();

                                        Intent intent = new Intent(getApplicationContext(), AllDrugs.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                                        chosenName = "name";
                                        drugsInAll.remove(deleteDrug);
                                    }
                                }});

                            System.out.println("ID FOR DAILY DRUG: " + idForDailyDrug);

                        }
                    }});

                //deleteDrug.put(String.valueOf(chosenName), FieldValue.delete());
            }
        });
    }


    public void btnAddDrug(View view) {
        int maxPosition = drugsInAll.size();
        Bundle bundle = new Bundle();
        bundle.putInt("max",maxPosition);
        Intent intent = new Intent(getApplicationContext(), AddDrugToAll.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void returnClicked(View view) {
        Intent intent = new Intent(this, MenuAll.class);
        startActivity(intent);
        finish();
    }
}

