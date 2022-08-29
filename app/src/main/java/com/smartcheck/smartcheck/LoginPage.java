package com.smartcheck.smartcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.smartcheck.smartcheck.Adapter.Medicineadapter;
import com.smartcheck.smartcheck.ModalClass.medicinemodalclass;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity {

    TextView signup;
    LinearLayout googlesignin;
    LinearLayout ll_signin;
    TextView termsandconditions;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN=100;
    TextView enteremail,tv_password;
        ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().hide();
        AlphaAnimation click_animation = new AlphaAnimation(1f, 0.8f);
        enteremail=findViewById(R.id.enteremail);
        tv_password=findViewById(R.id.tv_password);
        pd=new ProgressDialog(this);
        pd.setCancelable(false);



        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        termsandconditions=findViewById(R.id.termsandconditions);
        termsandconditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TermsandCondt.class));
            }
        });

        signup=findViewById(R.id.signup);
        ll_signin=findViewById(R.id.ll_signin);
        ll_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    pd.show();
                    pd.setContentView(R.layout.progess_anim);
                    pd.getWindow().setBackgroundDrawableResource(cn.pedant.SweetAlert.R.color.float_transparent);
                datacheck();
             //   pd.dismiss();

            }
        });
        googlesignin=findViewById(R.id.googlesignin);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(click_animation);
                Intent i=new Intent(getApplicationContext(),Register.class);
                startActivity(i);
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                pd.setContentView(R.layout.progess_anim);
                pd.getWindow().setBackgroundDrawableResource(cn.pedant.SweetAlert.R.color.float_transparent);
                v.startAnimation(click_animation);
                signIn();

            }
        });
    }







    private void datacheck() {

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        String url="https://script.google.com/macros/s/AKfycbwXIXqkXPHtHa67x2mEQvHbF8U4kdKA9OkIBg1f2BXfB6NvZttK24aZKrJkCrPp6oMclA/exec";
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = new JSONObject(response);
                    JSONArray arr = js.getJSONArray("data");
                    boolean x=false;




                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject j = arr.getJSONObject(i);
                        int id=j.getInt("id");
                        Constant.key=new String(String.valueOf(id));

                        String email = j.getString("email"),
                                password = j.getString("password");


                        if(email.equals(enteremail.getText().toString()) && password.equals(tv_password.getText().toString()))
                        {
                            Intent s=new Intent(getApplicationContext(),Dashboard.class);
                            Constant.google=0;
                            Constant.login=1;
                            startActivity(s);
                            x=true;
                            finish();
                            break;
                        }
                    }

                    if(!x)
                    Toast.makeText(LoginPage.this, "Invalid credentails!!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();




                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginPage.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue.add(mStringRequest);

    }














    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            pd.dismiss();
            Intent i=new Intent(getApplicationContext(),Dashboard.class);
            Constant.google=1;
            Constant.login=0;
            startActivity(i);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, e.getStatusCode(), Toast.LENGTH_SHORT).show();

        }
    }
}

