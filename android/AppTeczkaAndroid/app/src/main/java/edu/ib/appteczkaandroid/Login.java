package edu.ib.appteczkaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private TextView tvForgotLogin;
    private Button btnLogin;
    //W ODPOWIEDNIM FOLDERZE TRZEBA UMIESCIC I ZMIENIC URL
    private String URL = "http://10.0.2.2:80/login/login.php"; //to jest na emulator, jak chcecie testować na kompie, to ip localhosta trzeba zamiast 10.0.2.2
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmailL);
        etPassword = findViewById(R.id.etPasswordL);
        tvForgotLogin = findViewById(R.id.tvForgotLogin2);

        btnLogin = findViewById(R.id.btnLogin);

        email = password = "";

        tvForgotLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), .class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(intent);
                //finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail.getText().toString().equals("") || etPassword.getText()==null){
                    Toast.makeText(getApplicationContext(),"Wprowadź dane", Toast.LENGTH_SHORT).show();
                }
                else{
                Intent intent = new Intent(getApplicationContext(), MenuAll.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();}
            }
        });
    }

    public void btnLogin(View view) {
        //Intent intent = new Intent(getApplicationContext(), .class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //startActivity(intent);
        //finish();
    }

    /*public void btnLogin(View view) {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (!email.equals("") && !password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("res", response);
                    if (response.equals("success")) {

                        //UserInfo info = UserInfo.getInstance();
                        //info.setEmail(email);

                        //intent na otwarcie nowego okna
                    } else if (response.equals("failure")) {
                        Toast.makeText(getApplicationContext(), "Niepoprawny email lub hasło.", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();
                }
            }) {
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
        } else {
            Toast.makeText(getApplicationContext(), "Wszystkie pola muszą być wypełnione!", Toast.LENGTH_LONG).show();
        }
    }*/
}