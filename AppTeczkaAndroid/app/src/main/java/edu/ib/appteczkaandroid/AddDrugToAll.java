package edu.ib.appteczkaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddDrugToAll extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    private EditText etName, etDate;

    private int yearInt, monthInt, dayInt;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug_to_all);

        etName = findViewById(R.id.etDrugName);

        etDate = (EditText) findViewById(R.id.etDrugDateExp);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(etDate);

            }
        };

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddDrugToAll.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(EditText et){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        et.setText(dateFormat.format(calendar.getTime()));
    }

    public void btnBackOnClick(View view) {
    }

    public void btnAddDrugFinalOnClick(View view) {
        name = etName.getText().toString().trim();

        yearInt = calendar.get(Calendar.YEAR);
        monthInt = calendar.get(Calendar.MONTH)+1;
        dayInt = calendar.get(Calendar.DAY_OF_MONTH);

        //TUTAJ MUSI BY DODAWANIE DO BAZY, NO A PRZY ALLDRUGS TO ODCZYT Z BAZY


    }
}