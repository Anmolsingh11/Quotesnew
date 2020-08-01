package com.kavara.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuotesActivity extends AppCompatActivity implements IFirebaseLoadDone {
    ViewPager viewPager;
    QuotesAdapter adapter;
    DatabaseReference QuotesReference;
    IFirebaseLoadDone iFirebaseLoadDone;
    String categoryName = "";
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        title = findViewById(R.id.title);
        categoryName = getIntent().getStringExtra("CategoryName");

        title.setText(categoryName);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        QuotesReference = FirebaseDatabase.getInstance().getReference("QuotesNode").child(categoryName);

        iFirebaseLoadDone = this;

        loadQuotes();
        viewPager = findViewById(R.id.quote_view_pager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    private void loadQuotes() {
        QuotesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            List<QuotesModel> quotesModels = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot quoteSnapshot : dataSnapshot.getChildren()) {
                    quotesModels.add(quoteSnapshot.getValue(QuotesModel.class));
                    iFirebaseLoadDone.onFirebaseLoadSuccess(quotesModels);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }


    @Override
    public void onFirebaseLoadSuccess(List<QuotesModel> quotesModelList) {
        adapter = new QuotesAdapter(this, quotesModelList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
