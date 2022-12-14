package edu.ib.appteczkaandroid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.google.protobuf.StringValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AddDrugToAll extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    private EditText etName, etDate;

    private int yearInt, monthInt, dayInt, drugId;
    private String drugName, drugDate, year, month, day, emailUser;

    private int max;

    private DrugInAll drugInfo;


    private String[] keys;

    private Button btnAddDrug;
    private ImageButton btnBack;
    private ArrayList<DrugInAll> drugsInAll= new ArrayList<>();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug_to_all);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        emailUser = currentUser.getEmail();




        if(currentUser == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                drugsInAll = new ArrayList<>();
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
                                keys = new String[drugInAllList.size()];
                                int i = 0;
                                for (Map.Entry<String, Object> entry : dAListSorted.entrySet()) {
                                    Object race = entry.getValue();
                                    Gson gson = new Gson();
                                    DrugInAll drugInAll= gson.fromJson(String.valueOf(race), DrugInAll.class);
                                    keys[i] = entry.getKey();
                                    drugsInAll.add(drugInAll);
                                    System.out.println(drugsInAll.toString());
                                    i++;


                                }
                                max = Integer.parseInt(keys[0]);
                                for (int j = 1; j < keys.length; j++) {
                                    int temp  = Integer.parseInt(keys[j]);
                                    if(Integer.parseInt(keys[j])>max){
                                        max = temp;
                                    }
                                }
                                max += 1;


                                System.out.println("MAKSYMALNA WARTOSC: " + max);
                            }
                        } else {
                            max =0;
                        }
                        if (task.isComplete()) {


                            drugName = etName.getText().toString();
                            drugDate = etDate.getText().toString();

                            yearInt = calendar.get(Calendar.YEAR);
                            monthInt = calendar.get(Calendar.MONTH) + 1;
                            dayInt = calendar.get(Calendar.DAY_OF_MONTH);

                            year = String.valueOf(yearInt);
                            month = String.valueOf(monthInt);
                            day = String.valueOf(dayInt);
                            int zmiennaRobocza = 0;
                            List<Character> drugNameChars = drugName.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
                            List<Character> specialchars = new ArrayList<>();
                            specialchars.add(('.'));
                            specialchars.add('+');
                            specialchars.add(',');
                            specialchars.add('%');
                            specialchars.add('^');
                            specialchars.add('&');
                            specialchars.add('*');
                            specialchars.add('#');
                            specialchars.add('@');
                            specialchars.add('!');
                            specialchars.add('~');
                            specialchars.add(';');
                            specialchars.add('\n');
                            specialchars.add('/');
                            specialchars.add('`');
                            specialchars.add('|');
                            specialchars.add('{');
                            specialchars.add('}');
                            specialchars.add('-');
                            specialchars.add(':');
                            System.out.println("RACE 2:" + drugNameChars.toString());
                            for (int i = 0; i < drugNameChars.size(); i++) {
                                for(int j = 0; j < specialchars.size(); j++){
                                    if (drugNameChars.get(i) == (specialchars.get(j))) {
                                        Toast.makeText(AddDrugToAll.this, "Nazwa leku nie mo??e zawiera?? znak??w specjalnych.", Toast.LENGTH_SHORT).show();
                                        zmiennaRobocza = 1;
                                        break;
                                    }
                                }
                            }
                            if (zmiennaRobocza == 0) {
                                if (etDate.getText().equals("") || etName.getText() == (null)) {
                                    Toast.makeText(AddDrugToAll.this, "Aby doda?? lek nale??y wype??ni?? wszystkie pola.", Toast.LENGTH_SHORT).show();
                                } else {
                                    //FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Map<String, Object> data = new HashMap<>();
                                    drugInfo = new DrugInAll(drugName,drugDate);
                                    String maxStr = String.valueOf(max);
                                    data.put(maxStr, drugInfo);

                                    db.collection(String.valueOf(emailUser)).document("lekiWszystkie")
                                            .set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())

                                                System.out.println(maxStr + "," + drugInfo.toString());
                                            Toast.makeText(AddDrugToAll.this, "Lek zosta?? poprawnie dodany", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                        }}});

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
