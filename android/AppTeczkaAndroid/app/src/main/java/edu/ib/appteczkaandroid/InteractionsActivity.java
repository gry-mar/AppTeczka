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

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Map<String,Object> data = new HashMap<>();
//        data.put("button1", "try");
//        data.put("name","Anna");
//        data.put("lastName","Nowak");
//        Map<String,Boolean> data1 = new HashMap<>();
//        data1.put("0",false);
//        data1.put("1",true);
//
//        Map<String, Object> lekiOgol = new HashMap<>();
//        lekiOgol.put("1",new LekiOgolnie("aaaaa" , "11.02.3030"));
//
//        Map<String, Object> nowy = new HashMap<>();
//        nowy.put("1","ooouuu");
//
//
//        // TEN LEPSZY SPOSOB
//        db.collection("useremail@gmail.com").document("lekiNaDzien").set(nowy).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                    Toast.makeText(InteractionsActivity.this,"sposob lepszy v1",Toast.LENGTH_SHORT).show();
//            }
//        });
//        db.collection("useremail@gmail.com").document("lekiOgolne").set(lekiOgol).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                    Toast.makeText(InteractionsActivity.this,"sposob lepszy v2",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        // ten chjujowy mocno
//        db.collection("users").document("userData").set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                Toast.makeText(InteractionsActivity.this,"Hura",Toast.LENGTH_SHORT).show();
//            }
//        });
//        db.collection("users").document("user1").set(lekiOgol).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                    Toast.makeText(InteractionsActivity.this,"second done",Toast.LENGTH_SHORT).show();
//            }
//        });
//        db.collection("users").document("user1").set(nowy).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                    Toast.makeText(InteractionsActivity.this,"yts",Toast.LENGTH_SHORT).show();
//            }
//        });
//        DocumentReference doeRef =  db.collection("users").document("user2")
//                .collection("allData").document("toggleButtons");
//        doeRef.set(data1).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                    Toast.makeText(InteractionsActivity.this,"trzeci gituwa",Toast.LENGTH_SHORT).show();
//            }
//        });


    }
}