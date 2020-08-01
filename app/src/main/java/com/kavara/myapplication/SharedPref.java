package com.kavara.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SharedPref extends AppCompatActivity {
    RecyclerView recyclerView;
    SharedPreferences preferences;
    JSONObject saved;
    ImageButton copy,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);

        recyclerView = findViewById(R.id.recycler_view);
        copy = findViewById(R.id.Copy);
        delete = findViewById(R.id.Delete);


        preferences = getSharedPreferences("text", Context.MODE_PRIVATE);
        try {
            saved = new JSONObject(preferences.getString("saved",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SharedPref.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new Adapter());


    }


    public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
        @NonNull
        @Override
        public Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(SharedPref.this)
                    .inflate(R.layout.row_item,parent,false);
            Holder holder = new Holder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.Holder holder, int position) {
            try {
                holder.textView.setText(saved.getString("saved"+position));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return saved.length();
        }

        public class Holder extends RecyclerView.ViewHolder{
            TextView textView;
            public Holder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text_view);
            }
        }
    }
}
