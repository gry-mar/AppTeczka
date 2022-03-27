package edu.ib.appteczkaandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Login extends AppCompatActivity {

    //W ODPOWIEDNIM FOLDERZE TRZEBA UMIESCIC I ZMIENIC URL
    private String URL = "http://10.0.2.2:80/login/login.php"; //to jest na emulator, jak chcecie testować na kompie, to ip localhosta trzeba zamiast 10.0.2.2
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = password = "";
    }

    public void btnLogin(View view) {
        //COS W TYM STYLU Z EDIT TEXTOW TRZEBA BEDZIE
        //email = editTextEmail.getText().toString().trim();
        //password = editTextPassword.getText().toString().trim();
        if (!email.equals("") && !password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("res", response);
                    if (response.equals("success")) {
                        UserInfo info = UserInfo.getInstance();
                        info.setEmail(email);
                        //intent na otwarcie nowego okna
                    } else if (response.equals("failure")) {
                        Toast.makeText(getApplicationContext(), "Wrong email/password.", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(), "All fields have to be filled!", Toast.LENGTH_LONG).show();
        }
    }
}