package com.sellanddonate.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sellanddonate.app.R;
import com.sellanddonate.app.firebase.BidDetail;
import com.sellanddonate.app.firebase.MyAdds;
import com.sellanddonate.app.util.ImageLoadingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BidAdapter extends RecyclerView.Adapter<BidAdapter.MyViewHolder> {

    private List<BidDetail> ExploreDetailList;
    private List<String> categoryList;
    // OnClick_RecyclerVieww onClick_recyclerView;
    double lat, longt;
    Context context;


    public BidAdapter(List<MyAdds> exploreDetailList, List<String> catagory) {
        ExploreDetailList = new ArrayList<>();
        categoryList = catagory;


    }

    public BidAdapter(Context context, List<BidDetail> exploreDetailList) {
        ExploreDetailList = new ArrayList<>();
        this.context = context;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_bid, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindView();
    }

    @Override
    public int getItemCount() {
        return ExploreDetailList.size();
    }

    public void update(ArrayList<BidDetail> productList) {

        ExploreDetailList.addAll(productList);

        notifyDataSetChanged();
    }

    public interface OnClick_RecyclerVieww {
        void onAdClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, numberOfPics;
        TextView price, catagory;
        Button b_direction;
        ;

        // OnClick_RecyclerVieww onClick_ViewHolder;

        MyViewHolder(View itemView) {
            super(itemView);
            // this.onClick_ViewHolder=onClick_recyclerView;
            //  imageView = itemView.findViewById(R.id.ivImage);
            title = itemView.findViewById(R.id.tv_bid_title);
            // catagory = itemView.findViewById(R.id.txt_category);
            numberOfPics = itemView.findViewById(R.id.tv_bid_price);
            price = itemView.findViewById(R.id.tvbid_status);
            b_direction = itemView.findViewById(R.id.b_bid_direction);

            // itemView.setOnClickListener(this);
        }

        void bindView() {

            title.setText("Bid for " + ExploreDetailList.get(getAdapterPosition()).getTitle());
            numberOfPics.setText("Bidding Price: " + ExploreDetailList.get(getAdapterPosition()).getbidAmount());
            // catagory.setText("Posted Add In: "+categoryList.get(getAdapterPosition()));
            String statuss = ExploreDetailList.get(getAdapterPosition()).getStatus();
            if (statuss.equals("pending")) {
                b_direction.setVisibility(View.INVISIBLE);
                price.setText("Status: pending");

                price.setTextColor(Color.parseColor("#cccccc"));

            } else if (statuss.equals("reject")) {
                price.setText("Status: rejected");
                price.setTextColor(Color.parseColor("#EE0519"));
                b_direction.setVisibility(View.INVISIBLE);
            } else {
                price.setText("Status: Accepted");
                price.setTextColor(Color.parseColor("#128F17"));
                b_direction.setVisibility(View.VISIBLE);

                b_direction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lat = ExploreDetailList.get(getAdapterPosition()).getLat();
                        longt = ExploreDetailList.get(getAdapterPosition()).getLongt();
                        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", lat, longt, "destination");
                        Uri gmmIntentUri = Uri.parse(uri);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        context.startActivity(mapIntent);
                    }
                });

            }


        }



    }
}
