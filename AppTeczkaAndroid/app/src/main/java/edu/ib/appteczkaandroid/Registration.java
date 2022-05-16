package edu.ib.appteczkaandroid;

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

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private EditText etEmail, etPassword, etPassword2;
    private TextView tvLogin;

    //W ODPOWIEDNIM FOLDERZE TRZEBA UMIESCIC I ZMIENIC URL
    private String URL = "http://10.0.2.2:80/login/register.php"; //to jest na emulator, jak chcecie testować na kompie, to ip localhosta trzeba zamiast 10.0.2.2
    private String email, password, password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etEmail = findViewById(R.id.etEmailR);
        etPassword = findViewById(R.id.etPasswordR1);
        etPassword2 = findViewById(R.id.etPasswordR2);
        tvLogin = findViewById(R.id.tvHaveAcc2);

        email = password = password2 = "";

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
        password = etPassword.getText().toString().trim();
        password2 = etPassword2.getText().toString().trim();
        if(!password.equals(password2)){
            Toast.makeText(getApplicationContext(), "Hasła muszą być takie same!", Toast.LENGTH_LONG).show();
        }
        else if(!email.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("success")){
                        Toast.makeText(getApplicationContext(), "Rejestracja przebiegła pomyślnie!", Toast.LENGTH_LONG).show();

                    }else if(response.equals("failure")){
                        Toast.makeText(getApplicationContext(), "Coś poszło nie tak. Spróbuj ponownie.", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", email);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(getApplicationContext(), "Wszystkie pola muszą być wypełnione!", Toast.LENGTH_LONG).show();
        }
    }
}