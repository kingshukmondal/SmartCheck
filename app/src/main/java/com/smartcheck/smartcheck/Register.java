package com.smartcheck.smartcheck;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextInputEditText name, email, password, confrimpassword;
    LinearLayout ll_signup;
    LinearLayout craccsignup;

    ProgressDialog pd;

    String name1, email1, password1, cpassword;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);





        getSupportActionBar().hide();
        pd = new ProgressDialog(this);
        pd.setIcon(R.drawable.mainlogo);
        pd.setTitle("Registration");
        pd.setMessage("Loading................");
        pd.setCancelable(false);

        craccsignup=findViewById(R.id.craccsignup);
        craccsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginPage.class));
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confrimpassword = findViewById(R.id.confrimpassword);


        // mDatabase = FirebaseDatabase.getInstance().getReference();
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().toString().length() < 5) {
                    password.requestFocus();
                    password.setError("minimum 6 characters");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ll_signup = findViewById(R.id.ll_signup);
            ll_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1 = name.getText().toString();
                email1 = email.getText().toString();
                password1 = password.getText().toString();
                cpassword = confrimpassword.getText().toString();

                pd.show();
                pd.setContentView(R.layout.progess_anim);
                pd.getWindow().setBackgroundDrawableResource(cn.pedant.SweetAlert.R.color.float_transparent);

                if (name1.length() == 0) {
                    name.requestFocus();
                    name.setError("Name cannot be empty.");
                    pd.dismiss();
                } else if (email1.length() == 0) {
                    email.requestFocus();
                    email.setError("Email cannot be empty.");
                    pd.dismiss();
                } else if (password1.length() == 0) {
                    pd.dismiss();
                    password.requestFocus();
                    password.setError("Required");
                } else if (cpassword.length() == 0) {
                    pd.dismiss();
                    confrimpassword.requestFocus();
                    confrimpassword.setError("Required");
                } else if (password1.length() < 6) {
                    pd.dismiss();
                    password.requestFocus();
                    password.setError("Password should be minimum 6 digits");
                } else if (!email1.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    pd.dismiss();
                    email.requestFocus();
                    email.setError("Not a valid email address");
                } else if (!password1.equals(cpassword)) {
                    pd.dismiss();
                    confrimpassword.requestFocus();
                    confrimpassword.setError("wromg password");
                } else {
                    name.clearFocus();
                    email.clearFocus();
                    password.clearFocus();
                    confrimpassword.clearFocus();


                    // DataForRegsistration dr=new DataForRegsistration(email1,cpassword,name1,"none","5664");
                    sheetslogin();
                }
            }
        });

    }



    void sheetslogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbwXIXqkXPHtHa67x2mEQvHbF8U4kdKA9OkIBg1f2BXfB6NvZttK24aZKrJkCrPp6oMclA/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.dismiss();
                        if(response.equals("Success"))
                        {
                            startActivity(new Intent(getApplicationContext(),LoginPage.class));
                        }
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action", "addItem");
                parmas.put("email", email.getText().toString().trim());
                parmas.put("password", password.getText().toString().trim());
                parmas.put("name", name.getText().toString().trim());

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);
    }


}
