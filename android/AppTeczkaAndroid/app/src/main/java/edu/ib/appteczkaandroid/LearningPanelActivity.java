package edu.ib.appteczkaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LearningPanelActivity extends AppCompatActivity {

    private TextView tv1,tv2,tv3, tv4;

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

        setContentView(R.layout.activity_learning_panel);
        tv1 = findViewById(R.id.firstPoint);
        tv1.setText("1. Przejdź do początku ulotki");
        tv2 = findViewById(R.id.secondPoint);
        tv2.setText("2. Znajdź napis na górze strony: Ulotka dla pacjenta/ dołączona do opakowania"+
                ": informacja dla użytkownika");
        tv3 = findViewById(R.id.thirdPoint);
        tv3.setText("3. Znajdź nazwę leku, powinna być zaznaczona widocznie większą/ pogrubioną czcionką");
        tv4 = findViewById(R.id.fourPoint);
        tv4.setText("4. Nazwa substancji aktywnej powinna znajdować się pomiędzy tekstami z punktu 2 i 3");
    }

    public void btnReturnOnClick(View view) {
        finish();
    }
}