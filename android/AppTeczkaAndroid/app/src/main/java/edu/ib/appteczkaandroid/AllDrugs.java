package edu.ib.appteczkaandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllDrugs extends AppCompatActivity {

    private ArrayList<DrugInAll> drugsList;

    // tu powinna być klasa przechowująca obiekty {name, date}

    private String lek1, lek2, data1, data2, lek3, data3;
    private int state, rowId, rowIdOld;
    private String chosenName;
    private String chosenDate;
    //public AllDrugs(ArrayList<DrugInAll> drugsList, Context context) {
    //    this.drugsList = drugsList;
    //    this.context = context;
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_drugs);
        lek1 = "Lek1";
        lek2 = "Lek2";
        data1 = "06-06-2026";
        data2 = "08-08-2028";
        lek3 = "Lek3";
        data3 = "04-04-2024";
        state = 0;

        Button btnRemoveDrug = findViewById(R.id.btnRemoveDrug);

        //TU TRZEBA ZMIENIć NA DATE JAK JUŻ BĘDZIE BAZA i w ogole ustawić tak,
        // że cała ta lista ściągana z bazy będzie słownikiem, a po kliknięciu adda to sie doda nowa wartosc,
        // czyli trzeba będzie to wrzucić dodawanie do innej metody
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //po dodaniu czegoś do dawkowania

        Map<String,String> drug = new HashMap<String,String>();
        drug.put(lek1, data1);
        drug.put(lek2, data2);
        drug.put(lek3, data3);


        // TEN LEPSZY SPOSOB
        db.collection("useremail@gmail.com").document("lekiWszystkie")
                .set(drug).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(AllDrugs.this,"sposob lepszy v1",Toast.LENGTH_SHORT).show();
            }
        });


        TableLayout table = (TableLayout)AllDrugs.this.findViewById(R.id.tableAllDrugs);
        int i = 0;
        for (Map.Entry<String, String> drugEntry : drug.entrySet() ) {
            String key = drugEntry.getKey();
            String value = drugEntry.getValue();
            //drugsList.add(d, drugEntry.getValue());
            final TableRow row = new TableRow(this);

            TextView tv1 = new TextView(this);
            tv1.setText(key);
            tv1.setTextColor(Color.parseColor("#3e3e3e"));
            tv1.setGravity(Gravity.CENTER);
            tv1.setPadding(0,10,0,10);
            row.addView(tv1);

            TextView tv2 = new TextView(this);
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

    }


    public void btnAddDrug(View view) {

        /*Intent intent = new Intent(getApplicationContext(), AddDrugToAll.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();*/
    }

    public void btnRemoveDrug(View view) {
        //plus dodać usunięcie z bazy po tym wyznaczonym wyżej rowId i odświeżenie bazy po kazdym usunięciu,
        //żeby sie od nwoa te leki ladowały już bez tego usuniętego

    }
}
