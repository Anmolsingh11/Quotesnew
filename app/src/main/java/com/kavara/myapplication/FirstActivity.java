package com.kavara.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

public class FirstActivity extends AppCompatActivity {

    private Button btnCategory;
    Button btnshr, btnadd, btnprvcy, btnrate,btnbook;
    private WebView view;
     JSONObject[] saved = {new JSONObject()};
     SharedPreferences preferences;
     SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_first);

        //my Work
        preferences = getSharedPreferences("text", Context.MODE_PRIVATE);
        editor = preferences.edit();
        try {
            if(!preferences.getString("saved","").equals(""))
                saved[0] = new JSONObject(preferences.getString("saved",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //google ads
        MobileAds.initialize(this);
        loadAds();
        btnCategory = findViewById(R.id.category_btn);
        btnshr = findViewById(R.id.share_btn);
        btnadd = findViewById(R.id.add_btn);
        btnprvcy = findViewById(R.id.privecy_btn);
        btnrate = findViewById(R.id.rate_btn);
        btnbook = findViewById(R.id.book_btn);

        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(preferences.getString("saved","").equals(""))
                {
                    Toast.makeText(getApplicationContext(),"No Bookmarks Yet",Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(FirstActivity.this, SharedPref.class));
                }
            }
        });
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, CategoryActivity.class));

            }
        });
        final String appLink = ("http://play.google.com/store/apps/details?id=" + this.getPackageName());

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, AddQuotes.class));
            }
        });

        btnshr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(intent.EXTRA_SUBJECT, "Send");
                intent.putExtra(intent.EXTRA_TEXT, appLink);
                startActivity(Intent.createChooser(intent, "Share Using"));
            }
        });
        btnprvcy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = new WebView(FirstActivity.this);
                view.getSettings().setJavaScriptEnabled(true);
                view.loadUrl("file:///android_asset/privacy_policy.html");
                setContentView(view);
            }
        });

        btnrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RateApp();
            }
        });
    }




    private void RateApp() {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }

    private void loadAds() {

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (view != null) {
            startActivity(new Intent(FirstActivity.this, FirstActivity.class));
        } else {
            finish();
        }
    }
}
