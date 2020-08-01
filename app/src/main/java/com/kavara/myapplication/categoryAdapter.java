package com.kavara.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;
    private InterstitialAd interstitialAd;

    public categoryAdapter(List<CategoryModel> categoryModelList, InterstitialAd interstitialAd) {
        this.categoryModelList = categoryModelList;
        this.interstitialAd = interstitialAd;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(categoryModelList.get(position).getCategoryName());


    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
        }

        private void setData(final String categoryName) {
            this.categoryName.setText(categoryName);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /////////////////////////////////////////
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();

                        interstitialAd.loadAd(new AdRequest.Builder().build());

                        Intent intent = new Intent(itemView.getContext(), QuotesActivity.class);
                        intent.putExtra("CategoryName", categoryName);
                        itemView.getContext().startActivity(intent);
                    }
                });

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                    return;
                }


                /////////////////////////////////////////

                Intent intent=new Intent(itemView.getContext(),QuotesActivity.class);
                intent.putExtra("CategoryName", categoryName);
                itemView.getContext().startActivity(intent);
            }
        });
        }
    }
}
