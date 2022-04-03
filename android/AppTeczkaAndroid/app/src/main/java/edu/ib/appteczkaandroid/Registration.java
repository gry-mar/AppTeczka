package edu.ib.appteczkaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

    //W ODPOWIEDNIM FOLDERZE TRZEBA UMIESCIC I ZMIENIC URL
    private String URL = "http://10.0.2.2:80/login/register.php"; //to jest na emulator, jak chcecie testować na kompie, to ip localhosta trzeba zamiast 10.0.2.2
    private String email, password, password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = password = password2 = "";
    }
    
    public void btnRegistration(View view) {
        //email = editTextEmail.getText().toString().trim();
        //password = editTextPassword.getText().toString().trim();
        //password2 = editTextPassword2.getText().toString().trim();
        if(!password.equals(password2)){
            Toast.makeText(getApplicationContext(), "Passwords have to be equals!", Toast.LENGTH_LONG).show();
        }
        else if(!email.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("success")){
                        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();

                    }else if(response.equals("failure")){
                        Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(), "All fields have to be filled!", Toast.LENGTH_LONG).show();
        }
    }
}