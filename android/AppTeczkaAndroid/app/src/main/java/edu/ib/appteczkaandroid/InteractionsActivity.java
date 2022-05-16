package edu.ib.appteczkaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InteractionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactions);
    }

    public void btnReturnOnClick(View view) {
        finish();
    }

    public void compareTwo(View view) {
        Intent intent = new Intent(this, CompareTwoActivity.class);
        startActivity(intent);

    }

    public void openLearningPanelClicked(View view) {
        Intent intent = new Intent(this, LearningPanelActivity.class);
        startActivity(intent);
    }
}