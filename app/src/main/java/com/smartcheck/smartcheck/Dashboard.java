package com.smartcheck.smartcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Dashboard extends AppCompatActivity {

    LinearLayout raiseacomplain,pathologyprice,bloodavailbility,medicinecost,claimstatus,nearbyopitals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        AlphaAnimation click_animation = new AlphaAnimation(1f, 0.8f);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.specialgreen));
        }

        if(Constant.google==1) {
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Constant.key = personId;
                Uri personPhoto = acct.getPhotoUrl();
            }
        }

        raiseacomplain=findViewById(R.id.raiseacomplain);
        pathologyprice=findViewById(R.id.pathologyprice);
        bloodavailbility=findViewById(R.id.bloodavailbility);
        medicinecost=findViewById(R.id.medicinecost);
        claimstatus=findViewById(R.id.complainstatus);
        bloodavailbility=findViewById(R.id.bloodavailbility);
        nearbyopitals=findViewById(R.id.nearbylocation);
        ///////////////////////////////////////////////

        bloodavailbility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Bloodavailblitypage.class));
            }
        });

        nearbyopitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),nearby_hospitals.class));
            }
        });

        raiseacomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(click_animation);
                startActivity(new Intent(getApplicationContext(),Raiseacomplain.class));
            }
        });


        pathologyprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(click_animation);
                startActivity(new Intent(getApplicationContext(),Pathologycostpage.class));
            }
        });

        claimstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(click_animation);
                startActivity(new Intent(getApplicationContext(),ComplainStatus.class));
            }
        });

        medicinecost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(click_animation);
                startActivity(new Intent(getApplicationContext(),Mediciinecostpage.class));
            }
        });


        ImageView OpenBottomSheet = findViewById(R.id.bottomsheet);

        OpenBottomSheet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        v.startAnimation(click_animation);
                        BottomSheetDialogue bottomSheet = new BottomSheetDialogue();
                        bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
                    }
                });



    }
    static  int count=0;

    @Override
    public void onBackPressed() {
        count++;
        if(count==1) {
            Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(count==2)
            finishAffinity();
    }
}