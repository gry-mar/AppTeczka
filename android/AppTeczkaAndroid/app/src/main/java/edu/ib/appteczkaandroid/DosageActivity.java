package edu.ib.appteczkaandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DosageActivity extends AppCompatActivity {
    private String name;
    private String drugDate;
    private DrugDosaged drugInfo;
    private int drugId;
    EditText etDosagesPerDay, etDateEnd;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosage);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        drugDate = bundle.getString("date");
        drugId = bundle.getInt("id");
        Toast.makeText(DosageActivity.this, name + "  " + drugDate, Toast.LENGTH_SHORT).show();
        TextView name = findViewById(R.id.nazwaLekuDawkowanie);
        name.setText(new StringBuilder().append(this.name).append(", data ważności:\n").append(drugDate).toString());

        Button startDosage = findViewById(R.id.btnStartDosage);
        etDosagesPerDay = findViewById(R.id.etDosaged);
        etDateEnd = findViewById(R.id.etEndDate);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(etDateEnd);

            }
        };

        etDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DosageActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDosage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dosagesPerDay = etDosagesPerDay.getText().toString();
                String dateEnd = etDateEnd.getText().toString();
                System.out.println(etDateEnd);
                System.out.println(etDosagesPerDay);


                drugInfo = new DrugDosaged(DosageActivity.this.name, dateEnd, dosagesPerDay);
                System.out.println(drugInfo);
                if(etDosagesPerDay.getText().equals("") || etDateEnd.getText() ==(null)){
                    Toast.makeText(DosageActivity.this, "Aby zacząć dawkowanie należy wypełnić wszystkie wymagane pola.", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, Object> data = new HashMap<>();
                    data.put(String.valueOf(drugId),drugInfo);


                    db.collection("useremail@gmail.com").document("lekiNaDzien").set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Toast.makeText(DosageActivity.this,"udalo sie",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        //drugName.setText(customElements.get(position).getDrugName());
    }
    private void updateLabel(EditText et){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        et.setText(dateFormat.format(calendar.getTime()));
    }


    public void returnClicked(View view) {
        Intent intent = new Intent(this, AllDrugs.class);
        startActivity(intent);
        finish();
    }
}