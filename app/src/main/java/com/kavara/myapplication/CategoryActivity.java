package com.kavara.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Categories");
    private InterstitialAd mInterstitialAd;


    private RecyclerView recyclerView;
    List<CategoryModel> list;
    private Dialog loadingdialog;

    //Mywork
    LinearLayout connected,dissconnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        connected = findViewById(R.id.parentLayout);
        dissconnected = findViewById(R.id.disconnected);

        Check();

        loadAds();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        loadingdialog = new Dialog(this);
        loadingdialog.setContentView(R.layout.loading);
        loadingdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingdialog.setCancelable(false);
        loadingdialog.getWindow().setBackgroundDrawable(getDrawable(R.color.white));
        loadingdialog.show();
        final categoryAdapter adapter = new categoryAdapter(list, mInterstitialAd);
        recyclerView.setAdapter(adapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    list.add(dataSnapshot1.getValue(CategoryModel.class));
                }
                loadingdialog.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CategoryActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
                loadingdialog.dismiss();
            }
        });


    }

    public void Check()
    {
        ConnectivityManager manager  =  (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active = manager.getActiveNetworkInfo();
        if(null != active)
        {
            if(active.getType() == ConnectivityManager.TYPE_WIFI)
            {
                connected.setVisibility(View.VISIBLE);
                dissconnected.setVisibility(View.GONE);
            }
            if(active.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                connected.setVisibility(View.VISIBLE);
                dissconnected.setVisibility(View.GONE);
            }
        }else
        {
            connected.setVisibility(View.GONE);
            dissconnected.setVisibility(View.VISIBLE);
        }
    }

    private void loadAds() {


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_add_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }
}
