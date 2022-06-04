package edu.ib.appteczkaandroid;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CompareAllActivity extends AppCompatActivity {

    HashMap<Integer, String> drugMap = new HashMap<>();
    ArrayList<String> drugIdList = new ArrayList<>();
    private int drugCount;
    private RxNormService service;
    ArrayList<String> drugNames = new ArrayList<>();
    protected ArrayList<String> responseList = new ArrayList<>();
    int undefined = 0;
    protected ArrayAdapter<String> drugListAdapter;
    ListView listView;

    private String emailUser;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        emailUser = currentUser.getEmail();

        if(currentUser == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_compare_all);
        service = new RxNormService(this);
        getData();
        System.out.println(drugNames.toString());
        drugCount = drugNames.size();
        System.out.println(drugCount);
    }
    private void onDrugsCompare(ArrayList<String> response, ArrayList<String> drugs, boolean isSuccess){
        if(!isSuccess){
            System.out.println("Nie znaleziono interakcji pomiędzy wszystkimi lekami");

        }else{
            if(undefined>0){
                Toast.makeText(this,"Nie znaleziono wszystkich leków, aby uzyskać dostęp do większej bazy leków zachęcamy do zakupu Premium", Toast.LENGTH_LONG).show();
            }

            System.out.println(response.toString());
            for(String s: response){
                responseList.add(s);
            }
            setupList();
            System.out.println("Response  "+response.toString());

        }


    }
    private void onDrugIdGet(String id, int reqId, boolean isSuccess){
        if(!isSuccess){
            System.out.println("Nie znaleziono takiego leku, sprawdź czy został wpisany poprawnie");
            undefined +=1;
        }
        try {
            Integer val = Integer.parseInt(id);
            drugMap.put(reqId, id);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();}
        System.out.println("DrugMapVals"+drugMap.values().toString());
        if(drugMap.size()==drugCount-undefined && drugMap.size()>1){
            for(String v: drugMap.values()){

                    drugIdList.add(v);

            }
            service.getInteractionsWithAll(drugIdList,this::onDrugsCompare);

        }}

        private void getData(){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection(String.valueOf(emailUser)).document("lekiWszystkie");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            System.out.println(document.getData());
                            Map<String, Object> drugInAllList =  document.getData();
                            for(String k : drugInAllList.keySet()){
                                drugNames.add(k);
                                System.out.println(k.toString());
                                System.out.println(k.getClass());
                            }

                            System.out.println(drugNames.toString());

                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }

    public void onCompareClicked(View view) {
      System.out.println(drugNames.toString());
        drugCount = drugNames.size();
        if(drugIdList.size() <2){
            Toast.makeText(this, "Nie znaleziono żadnych leków do porównania interakcji, zachęcamy do zakupu Premium", Toast.LENGTH_LONG).show();
        }else {
            for (int i = 0; i < drugCount; i++) {
                service.getDrugsId(drugNames.get(i), this, this::onDrugIdGet, i);
            }
        }
    }

    private void setupList(){

        drugListAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_activated_1, responseList);
        listView = findViewById(R.id.list_view);
        listView.setAdapter(drugListAdapter);

    }

    public void btnReturnOnClick(View view) {
        finish();
    }
}