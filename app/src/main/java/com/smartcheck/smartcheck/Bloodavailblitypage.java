package com.smartcheck.smartcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Bloodavailblitypage extends AppCompatActivity {


    LinearLayout receiver,Donor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodavailblitypage);
        getSupportActionBar().hide();
        DonorPager firstFragment = new DonorPager();
        BloodReceiver secondFragment = new BloodReceiver();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.specialgreen));
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, secondFragment).commit();

        Donor=findViewById(R.id.Donor);
        Donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, firstFragment).commit();
            }
        });

        receiver=findViewById(R.id.receiver);
        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, secondFragment).commit();
            }
        });












    }





}