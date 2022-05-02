package edu.ib.appteczkaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CompareTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_two);

    }

    public void btnReturnOnClick(View view) {
        finish();
    }

    public void compareTwoClicked(View view) {
        TextView tvResult = findViewById(R.id.tvResult);
        EditText edtFirst = findViewById(R.id.edtDrug1Name);
        EditText edtSecond = findViewById(R.id.edtDrug2Name);
        String firstDrug = edtFirst.getText().toString();
        String secondDrug = edtSecond.getText().toString();
        RxNormService service = new RxNormService(this, tvResult);
        final Handler handler = new Handler(Looper.getMainLooper());
        final Context context = getApplicationContext();
        handler.post(new Runnable() {
            @Override
            public void run() {
                RxNormService service1 = new RxNormService(CompareTwoActivity.this, tvResult);
                service.getDrugsId(firstDrug, CompareTwoActivity.this);
                String first = RxNormService.id;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RxNormService service2 = new RxNormService(CompareTwoActivity.this, tvResult);
                        service.getDrugsId(secondDrug, CompareTwoActivity.this);
                        String second = RxNormService.id;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                service.getInteractionsWithTwoDrugs(first, second);
                            }
                        }, 1000);
                    }
                }, 1000);

            }
        });
    }


}