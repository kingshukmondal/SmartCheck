package com.smartcheck.smartcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.androidgamesdk.gametextinput.Listener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smartcheck.smartcheck.ModalClass.DataForRegsistration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    TextInputEditText name,email,password,confrimpassword;
    LinearLayout ll_signup;



    String name1,email1,password1,cpassword;
    private DatabaseReference mDatabase;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confrimpassword=findViewById(R.id.confrimpassword);



       // mDatabase = FirebaseDatabase.getInstance().getReference();
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(password.getText().toString().length()<5)
                {
                    password.requestFocus();
                    password.setError("minimum 6 characters");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ll_signup=findViewById(R.id.ll_signup);
        ll_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1 = name.getText().toString();
                email1 = email.getText().toString();
                password1 = password.getText().toString();
                cpassword = confrimpassword.getText().toString();

                Login_Sucess();


               /* if (name1.length() == 0) {
                    name.requestFocus();
                    name.setError("Name cannot be empty.");
                }
                   else if (email1.length() == 0) {
                    email.requestFocus();
                    email.setError("Email cannot be empty.");
                } else if (password1.length() == 0) {
                    password.requestFocus();
                    password.setError("Required");
                } else if (cpassword.length() == 0) {
                    confrimpassword.requestFocus();
                    confrimpassword.setError("Required");
                } else if (password1.length() < 6) {
                    password.requestFocus();
                    password.setError("Password should be minimum 6 digits");
                } else if (!email1.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    email.requestFocus();
                    email.setError("Not a valid email address");
                } else if (!password1.equals(cpassword)) {
                    confrimpassword.requestFocus();
                    confrimpassword.setError("wromg password");
                } else {
                    name.clearFocus();
                    email.clearFocus();
                    password.clearFocus();
                    confrimpassword.clearFocus();


                   // DataForRegsistration dr=new DataForRegsistration(email1,cpassword,name1,"none","5664");
                   LoginSucess();
                }*/

            }
        });

    }



   /* public  void letsregister(DataForRegsistration dr){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="https://igmivigisfutech.com/lashesh2u/api/Api/registration";
        final  StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jobjResult = new JSONObject(response);
                    int status=jobjResult.getInt("status");
                    String message=jobjResult.getString("message");

                    if(status==1)
                    {
                        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
          {

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email1);
                params.put("pass",password1);
                params.put("first_name", name1);
                params.put("last_name","sdhasdoajso");
                params.put("mobile","10000000");
                return params;
            }
        };
        queue.add(sr);
    }*/








    private void Login_Sucess() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://igmivigisfutech.com/lashesh2u/api/Api/registration",
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        try {


                            JSONObject jobjResult = new JSONObject(response);


                            int status = jobjResult.getInt("status");
                            String message = jobjResult.getString("message");


                            if (status==1) {

                                Toast.makeText(Register.this, "successfull", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplicationContext()
                                        , "message"+message, Toast.LENGTH_LONG).show();


                            }


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast toast =  Toast.makeText(Register.this, "Error in Login", Toast.LENGTH_SHORT);
                    }
                }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email.getText().toString().trim());
                params.put("pass",password.getText().toString().trim());
                params.put("first_name",name.getText().toString().trim());
                params.put("last_name","asdzxzxcx");
                params.put("mobile","8955530145");
                return params;

            }



        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance().addToRequestque(stringRequest);


    }





}