package com.smartcheck.smartcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TermsandCondt extends AppCompatActivity {

    WebView webterms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_condt);
        getSupportActionBar().hide();
        AlphaAnimation click_animation = new AlphaAnimation(1f, 0.8f);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        webterms = findViewById(R.id.webterms);
        WebSettings webSettings = webterms.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webterms.setWebViewClient(new Callback());
        webterms.loadUrl("https://www.app-privacy-policy.com/live.php?token=cwaj1C7rAEpk7qxwKhGYJj0ydXpfIsTm");
    }


    private static class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            // By returning false you're telling Android that, this is my website, so, do not override;
            // let WebView load the page.
            return false;
        }
    }
}