package com.smartcheck.smartcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    LinearLayout raiseacomplain,pathologyprice,bloodavailbility,medicinecost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.specialgreen));
        }

        raiseacomplain=findViewById(R.id.raiseacomplain);
        pathologyprice=findViewById(R.id.pathologyprice);
        bloodavailbility=findViewById(R.id.bloodavailbility);
        medicinecost=findViewById(R.id.medicinecost);
        ///////////////////////////////////////////////



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