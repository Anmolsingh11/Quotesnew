package com.kavara.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddQuotes extends AppCompatActivity {
    EditText data;
    EditText userName;
    Button submit;
    DatabaseReference reff;
    long maxid = 0;
    Add add;
    ScrollView connected,discconnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quotes);
        data = findViewById(R.id.editText);
        userName = findViewById(R.id.editText1);
        submit = findViewById(R.id.submit);
        add = new Add();
        reff = FirebaseDatabase.getInstance().getReference().child("Add");
        connected = findViewById(R.id.connected);
        discconnected = findViewById(R.id.disconnected);


        Check();

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxid = (dataSnapshot.getChildrenCount());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getText().toString().isEmpty()) {
                    Toast.makeText(AddQuotes.this, "Please Enter a Quote ", Toast.LENGTH_SHORT).show();
                } else {
                    String Data = data.getText().toString().trim();
                    String Name = userName.getText().toString().trim();
                    add.setQuote(Data);
                    add.setName(Name);
                    reff.child(String.valueOf(maxid + 1)).setValue(add);
                    Toast.makeText(AddQuotes.this, "Thanks for Your Contribution. we'll add the Quote as soon as possible.", Toast.LENGTH_LONG).show();
                }
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
            }
            if(active.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                connected.setVisibility(View.VISIBLE);
            }
        }else
        {
            connected.setVisibility(View.GONE);
            discconnected.setVisibility(View.VISIBLE);
        }
    }
}
