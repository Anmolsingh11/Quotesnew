package com.kavara.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class QuotesAdapter extends PagerAdapter {
    JSONObject saved = new JSONObject();
     SharedPreferences preferences;
     SharedPreferences.Editor editor;
    Context context;
    List<QuotesModel> quotesModelList;
    LayoutInflater inflater;
    ImageButton book;

    public QuotesAdapter(Context context, List<QuotesModel> quotesModelList) {
        this.context = context;
        this.quotesModelList = quotesModelList;
        inflater = LayoutInflater.from(context);


    }



    @Override
    public int getCount() {
        return quotesModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        final View view = inflater.inflate(R.layout.quotes_item, container, false);
        book = view.findViewById(R.id.bookmark);
        final TextView quote = view.findViewById(R.id.quote_tv);
        final TextView author = view.findViewById(R.id.author_name);
        view.findViewById(R.id.copyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = quotesModelList.get(position).getQuotes();
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", s);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "text copy", Toast.LENGTH_SHORT).show();

            }
        });
        view.findViewById(R.id.share_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "choose an option...", Toast.LENGTH_SHORT).show();
                String s = quotesModelList.get(position).getQuotes();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Quoting Mantra");
                intent.putExtra(Intent.EXTRA_TEXT, s);
                context.startActivity(Intent.createChooser(intent, "Share to..."));
            }
        });

        // Added Work


        preferences = context.getSharedPreferences("text", Context.MODE_PRIVATE);
        editor = preferences.edit();
        try {
            String s = quotesModelList.get(position).getQuotes();
            if(!preferences.getString("saved","").equals(""))
                saved = new JSONObject(preferences.getString("saved",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = quotesModelList.get(position).getQuotes();
                if(!s.equals(""))
                {
                    try {
                        if(!preferences.getString("saved","").equals(""))
                        {
                            saved = new JSONObject(preferences.getString("saved",""));
                            saved.put("saved"+ saved.length(),s);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    editor.putString("saved", saved.toString());
                    editor.apply();
                    Toast.makeText(context, "SuccessFully Bookmarked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        quote.setText(quotesModelList.get(position).getQuotes());
        author.setText(quotesModelList.get(position).getAuthor());
        container.addView(view);
        return view;
    }

}