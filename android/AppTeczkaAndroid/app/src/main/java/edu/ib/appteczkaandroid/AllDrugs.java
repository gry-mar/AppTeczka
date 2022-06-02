package edu.ib.appteczkaandroid;

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

public class AllDrugs extends AppCompatActivity {

    private ArrayList<DrugInAll> drugsList;

    private String drugName, drugExpDate;
    private int state, rowId, rowIdOld;
    private String chosenName;
    private String chosenDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_drugs);
<<<<<<< HEAD

=======
        lek1 = "Simvastatin";
        lek2 = "Lisinopril";
        data1 = "06-06-2026";
        data2 = "08-08-2028";
        lek3 = "Losartan";
        data3 = "04-04-2024";
>>>>>>> 4aad605cb522c48c311fdff9f932c76e393acd46
        state = 0;

        Button btnRemoveDrug = findViewById(R.id.btnRemoveDrug);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,String> drug = new HashMap<String,String>();

        db.collection("useremail@gmail.com").document("lekiWszystkie")
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
                    for(int i=0; i<task.getResult().getData().size(); i++){
                        splitdrugs[i] = splitdrugs[i].replace("{", "");
                        splitdrugs[i] = splitdrugs[i].replace("}", "");
                        splitdrugs[i] = splitdrugs[i].replace("[", "");
                        splitdrugs[i] = splitdrugs[i].replace("]", "");
                        String[] splitOneDrug = splitdrugs[i].split("=");
                        drug.put(String.valueOf(splitOneDrug[0]), String.valueOf(splitOneDrug[1]));

                    }

                    TableLayout table = (TableLayout)AllDrugs.this.findViewById(R.id.tableAllDrugs);
                    int i = 0;
                    for (Map.Entry<String, String> drugEntry : drug.entrySet() ) {
                        String key = drugEntry.getKey();
                        String value = drugEntry.getValue();
                        //drugsList.add(d, drugEntry.getValue());
                        final TableRow row = new TableRow(AllDrugs.this);

                        TextView tv1 = new TextView(AllDrugs.this);
                        tv1.setText(key);
                        tv1.setTextColor(Color.parseColor("#3e3e3e"));
                        tv1.setGravity(Gravity.CENTER);
                        tv1.setPadding(0,10,0,10);
                        row.addView(tv1);

                        TextView tv2 = new TextView(AllDrugs.this);
                        tv2.setText(value);
                        tv2.setTextColor(Color.parseColor("#3e3e3e"));
                        tv2.setGravity(Gravity.CENTER);
                        tv2.setPadding(0,10,0,10);
                        row.addView(tv2);

                        row.setId(i);
                        row.setClickable(true);
                        row.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(state == 0){
                                    row.setBackgroundColor(Color.parseColor("#adadad"));
                                    state = 1;
                                    System.out.println("3 " + row.getId());
                                    rowId = row.getId();
                                    rowIdOld = rowId;

                                    TextView name = (TextView) row.getChildAt(0);
                                    TextView date= (TextView) row.getChildAt(1);
                                    chosenName = name.getText().toString();
                                    chosenDate = date.getText().toString();
                                    System.out.println( chosenName);
                                    System.out.println(chosenDate);

                                }else if(state == 1){
                                    row.setBackgroundColor(Color.TRANSPARENT);
                                    state = 0;
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
                            if((chosenDate != null) || (chosenName != null)) {
                                Intent intent = new Intent(AllDrugs.this, DosageActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("name",  chosenName);
                                bundle.putString("date", chosenDate);
                                bundle.putInt("id", rowId);
                                intent.putExtras(bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                System.out.println(String.valueOf(intent));
                                finish();
                            }else{
                                Toast.makeText(AllDrugs.this,"Aby przejść do dawkowania, " +
                                        "najpierw zaznacz lek.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    table.requestLayout();


                } else {
                    Toast.makeText(AllDrugs.this,"Wystąpił błąd. Proszę spróbować ponownie.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRemoveDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection("useremail@gmail.com").document("lekiWszystkie");
                Map<String, Object> updates = new HashMap<>();
                //PRZEROBIC NAZWY

                Map<String, Object> deleteSong = new HashMap<>();
                deleteSong.put("Lek2", FieldValue.delete());

                FirebaseFirestore.getInstance()
                        .collection("useremail@gmail.com")
                        .document("lekiWszystkie")
                        .update(deleteSong);
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

