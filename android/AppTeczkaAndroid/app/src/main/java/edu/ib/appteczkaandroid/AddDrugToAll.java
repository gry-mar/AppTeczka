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
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.protobuf.StringValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddDrugToAll extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    private EditText etName, etDate;

    private int yearInt, monthInt, dayInt, drugId;
    private String drugName, drugDate, year, month, day;

    private DrugInAll drugInfo;

    private Button btnAddDrug;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug_to_all);


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(etDate);

            }
        };

        etName = findViewById(R.id.etDrugName);
        etDate = findViewById(R.id.etDrugDateExp);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddDrugToAll.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnAddDrug = findViewById(R.id.btnAddDrugFinal);

        btnAddDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drugName = etName.getText().toString();
                drugDate = etDate.getText().toString();

                yearInt = calendar.get(Calendar.YEAR);
                monthInt = calendar.get(Calendar.MONTH)+1;
                dayInt = calendar.get(Calendar.DAY_OF_MONTH);

                year = String.valueOf(yearInt);
                month = String.valueOf(monthInt);
                day = String.valueOf(dayInt);

                if(etDate.getText().equals("") || etName.getText() ==(null)){
                    Toast.makeText(AddDrugToAll.this, "Aby dodać lek należy wypełnić wszystkie pola.", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, Object> data = new HashMap<>();
                    data.put(drugName, drugDate);

                    db.collection("useremail@gmail.com").document("lekiWszystkie")
                            .set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Toast.makeText(AddDrugToAll.this,"Lek został poprawnie dodany",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AllDrugs.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    private void updateLabel(EditText et){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        et.setText(dateFormat.format(calendar.getTime()));
    }
}
