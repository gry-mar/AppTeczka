package edu.ib.appteczkaandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//<<<<<<< HEAD
//=======
//<<<<<<< Updated upstream
//=======
//<<<<<<< HEAD
//=======
//>>>>>>> Stashed changes
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private EditText etEmail, etPassword, etPassword2;
    private TextView tvLogin;
    private String email, password, password2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etEmail = findViewById(R.id.etEmailR);
        etPassword = findViewById(R.id.etPasswordR1);
        etPassword2 = findViewById(R.id.etPasswordR2);
        tvLogin = findViewById(R.id.tvHaveAcc2);
        mAuth = FirebaseAuth.getInstance();
        //email = password = password2 = "";

        tvLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    
    public void btnRegistration(View view) {
        email = etEmail.getText().toString().trim();
        FirebaseUser user = mAuth.getCurrentUser();
        password = etPassword.getText().toString().trim();
        password2 = etPassword2.getText().toString().trim();
        System.out.println(user);
        if(!password.equals(password2)){
            Toast.makeText(getApplicationContext(), "Hasła muszą być takie same!", Toast.LENGTH_LONG).show();
        }
        else if(!email.equals("") && !password.equals("")){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Registration.this, "Rejestracja przebiegła pomyślnie.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MenuAll.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }else if(user!=null){
                                Toast.makeText(Registration.this, "Użytkownik o takim adresie email ma już konto.",
                                        Toast.LENGTH_SHORT).show();
                            } else if((password.length()<6) || (password2.length()<6)){
                                Toast.makeText(Registration.this, "Hasło musi mieć długość co najmniej 6 znaków.",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Registration.this, "Błąd rejestracji.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }else{
            Toast.makeText(getApplicationContext(), "Wszystkie pola muszą być wypełnione!", Toast.LENGTH_LONG).show();
        }
    }
}