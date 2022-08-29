package com.smartcheck.smartcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class nearby_hospitals extends AppCompatActivity {
    WebView webView;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_hospitals);

        getSupportActionBar().hide();
        AlphaAnimation click_animation = new AlphaAnimation(1f, 0.8f);
        pd=new ProgressDialog(this);
        pd.setCancelable(false);



        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.specialgreen));
        }

            webView = findViewById(R.id.webview);
            WebSettings webSettings = webView.getSettings();
        pd.show();
        pd.setContentView(R.layout.progess_anim);
        pd.getWindow().setBackgroundDrawableResource(cn.pedant.SweetAlert.R.color.float_transparent);
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new Callback());
            webView.loadUrl("https://www.google.com/search?rlz=1C1CHBF_enIN990IN990&tbs=lf:1,lf_ui:4&tbm=lcl&sxsrf=ALiCzsbUHUa-kq3gC-CnLB6jHRLCF4cw-Q:1661759090301&q=nearby+hospitals+google+map&rflfq=1&num=10&ved=2ahUKEwjFgZ6Cx-v5AhXfR2wGHTEiAo0QtgN6BAgiEAU#rlfi=hd:;si:;mv:[[23.5840976,87.36054349999999],[23.4891607,87.2664211]];tbs:lrf:!1m4!1u3!2m2!3m1!1e1!2m1!1e3!3sIAE,lf:1,lf_ui:4");
            pd.dismiss();
    }


        private class Callback extends WebViewClient {
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                // By returning false you're telling Android that, this is my website, so, do not override;
                // let WebView load the page.
                return false;
            }
        }
    }
