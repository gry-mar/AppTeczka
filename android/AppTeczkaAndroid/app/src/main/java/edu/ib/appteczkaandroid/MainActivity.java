package edu.ib.appteczkaandroid;

import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void btnGo(View view) {
        Intent intent = new Intent(this, PlannerController.class);
        startActivity(intent);
        finish();
       // Intent intent = new Intent(this,InteractionsActivity.class);
        //startActivity(intent);
    }

}