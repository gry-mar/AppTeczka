package edu.ib.appteczkaandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

public class AllDrugs extends AppCompatActivity {

    private ArrayList<DrugInAll> drugsList;

    private String drugName, drugExpDate;
    private int state, rowId, rowIdOld;
    private String chosenName, chosenDate, emailUser;
    private int drugId;

    private FirebaseAuth mAuth;

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

        Map<String,String> drug = new HashMap<String,String>();

        db.collection(String.valueOf(emailUser)).document("lekiWszystkie")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();

                    ArrayList<Map<String, Object>> drugs = new ArrayList<>();
                    drugs.add(task.getResult().getData());
                    System.out.println(drugs);

                    String[] splitdrugs = drugs.toString().split(",");
                    if((task.getResult().getData() == null)) {
                        System.out.println("dupalekkkkk");
                    }else {
                        for (int i = 0; i < Objects.requireNonNull(task.getResult().getData()).size(); i++) {
                            splitdrugs[i] = splitdrugs[i].replace("{", "");
                            splitdrugs[i] = splitdrugs[i].replace("}", "");
                            splitdrugs[i] = splitdrugs[i].replace("[", "");
                            splitdrugs[i] = splitdrugs[i].replace("]", "");
                            String[] splitOneDrug = splitdrugs[i].split("=");
                            drug.put(String.valueOf(splitOneDrug[0]), String.valueOf(splitOneDrug[1]));

                        }

                        TableLayout table = (TableLayout) AllDrugs.this.findViewById(R.id.tableAllDrugs);
                        int i = 0;
                        for (Map.Entry<String, String> drugEntry : drug.entrySet()) {
                            String key = drugEntry.getKey();
                            String value = drugEntry.getValue();
                            final TableRow row = new TableRow(AllDrugs.this);

                            TextView tv1 = new TextView(AllDrugs.this);
                            tv1.setText(key);
                            tv1.setTextColor(Color.parseColor("#3e3e3e"));
                            tv1.setGravity(Gravity.CENTER);
                            tv1.setPadding(0, 10, 0, 10);
                            row.addView(tv1);

                            TextView tv2 = new TextView(AllDrugs.this);
                            tv2.setText(value);
                            tv2.setTextColor(Color.parseColor("#3e3e3e"));
                            tv2.setGravity(Gravity.CENTER);
                            tv2.setPadding(0, 10, 0, 10);
                            row.addView(tv2);

                            row.setId(i);
                            row.setClickable(true);
                            row.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (state == 0) {
                                        row.setBackgroundColor(Color.parseColor("#adadad"));
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
                                            state = 0;
                                        } else {
                                            Toast.makeText(AllDrugs.this, "Nie można równocześnie zaznaczyć " +
                                                    "więcej niż jednego leku.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });

                            table.addView(row);
                            i++;
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
                                    intent.putExtras(bundle);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    System.out.println(String.valueOf(intent));
                                    finish();
                                } else {
                                    Toast.makeText(AllDrugs.this, "Aby przejść do dawkowania, " +
                                            "najpierw zaznacz lek.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        table.requestLayout();
                    }


                } else {
                    Toast.makeText(AllDrugs.this,"Wystąpił błąd. Proszę spróbować ponownie.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRemoveDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection(String.valueOf(emailUser)).document("lekiWszystkie");
                Map<String, Object> updates = new HashMap<>();

                Map<String, Object> deleteDrug = new HashMap<>();
                Map<String, Object> deleteDrug2 = new HashMap<>();



                deleteDrug.put(String.valueOf(chosenName), FieldValue.delete());
                FirebaseFirestore.getInstance()
                        .collection(String.valueOf(emailUser))
                        .document("lekiWszystkie")
                        .update(deleteDrug);

                deleteDrug2.put(String.valueOf(rowId), FieldValue.delete());
                FirebaseFirestore.getInstance()
                        .collection(String.valueOf(emailUser))
                        .document("lekiNaDzien")
                        .update(deleteDrug2);

                db.collection(String.valueOf(emailUser)).document("togglebuttons").delete();

                Intent intent = new Intent(getApplicationContext(), AllDrugs.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

                chosenName = "name";

            }
        });
    }


    public void btnAddDrug(View view) {
        Intent intent = new Intent(getApplicationContext(), AddDrugToAll.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void returnClicked(View view) {
        Intent intent = new Intent(this, MenuAll.class);
        startActivity(intent);
        finish();
    }
}

