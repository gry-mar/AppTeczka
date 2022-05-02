package edu.ib.appteczkaandroid;

import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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