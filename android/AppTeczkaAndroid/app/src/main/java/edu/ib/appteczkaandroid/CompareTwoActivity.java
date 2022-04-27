package edu.ib.appteczkaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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
        RxNormService service = new RxNormService(this,tvResult);
        service.getInteractionsWithTwoDrugs(firstDrug, secondDrug);

            }
}