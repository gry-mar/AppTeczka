package edu.ib.appteczkaandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InteractionsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String emailUser;

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

        setContentView(R.layout.activity_interactions);
    }

    public void btnReturnOnClick(View view) {
        Intent intent = new Intent(this, MenuAll.class);
        startActivity(intent);
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

    public void onCompareWithAllClicked(View view) {
        Intent intent = new Intent(this, CompareAllActivity.class);
        startActivity(intent);




    }
}