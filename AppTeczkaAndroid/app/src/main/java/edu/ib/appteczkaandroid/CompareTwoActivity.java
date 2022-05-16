package edu.ib.appteczkaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


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
        RequestQueue queue = Volley.newRequestQueue(this);
        TextView tvResult = findViewById(R.id.tvResult);
        String url = "https://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis=207106+152923";
        System.out.println(url);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        res -> {
                            Results results = gson.fromJson(res.toString(), Results.class);

                            try {
                                String disc = results.getNlmDisclaimer();
                                tvResult.setText(disc);




                            } catch (Exception e) {
                                System.out.println("No description");

                            }
                        }, error -> {
                    System.out.println("error");

                });
                queue.add(stringRequest);
            }

}