package edu.ib.appteczkaandroid;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashMap;



public class CompareTwoActivity extends AppCompatActivity {

    HashMap<Integer, String> drugMap = new HashMap<>();
    private int drugCount;
    private TextView tvResult;
    private RxNormService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_two);
        tvResult = findViewById(R.id.tvResult);
        service = new RxNormService(this);

    }

    public void btnReturnOnClick(View view) {
        finish();
    }

    private void onDrugCompare(String response, String idFirst, String idSecond, boolean isSuccess){
        if(!isSuccess){
            tvResult.setText("Nie znaleziono interakcji");
        }else{
            tvResult.setText(response);
        }


    }

    private void onDrugIdGet(String id, int reqId, boolean isSuccess){
        if(!isSuccess){
           tvResult.setText("Nie znaleziono takiego leku, sprawdź czy został wpisany poprawnie");
        }
        drugMap.put(reqId, id);
        if(drugMap.size()==2){
            String first = drugMap.get(0);
            String second = drugMap.get(1);
            service.getInteractionsWithTwoDrugs(first,second,this::onDrugCompare);

        }

    }

    public void compareTwoClicked(View view) {
        EditText edtFirst = findViewById(R.id.edtDrug1Name);
        EditText edtSecond = findViewById(R.id.edtDrug2Name);
        String firstDrug = edtFirst.getText().toString();
        String secondDrug = edtSecond.getText().toString();
        service.getDrugsId(firstDrug, this,this::onDrugIdGet, 0);
        service.getDrugsId(secondDrug, this,this::onDrugIdGet, 1);

    }


}