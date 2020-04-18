package com.sellanddonate.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sellanddonate.app.R;
import com.sellanddonate.app.firebase.MyAdds;
import com.sellanddonate.app.firebase.SellerIdDetail;
import com.sellanddonate.app.ui.activites.Seller_id_detailActivity;
import com.sellanddonate.app.ui.myaddtab.ui.main.Seller_id_detail;
import com.sellanddonate.app.util.ImageLoadingUtils;

import java.util.ArrayList;
import java.util.List;

public class SellerIdAdpater extends RecyclerView.Adapter<SellerIdAdpater.MyViewHolder> {

    private List<SellerIdDetail> ExploreDetailList;
   // private List<String> categoryList;
    OnClick_RecyclerVieww onClick_recyclerView;


    public SellerIdAdpater(List<MyAdds> exploreDetailList,  OnClick_RecyclerVieww mOnClick_recyclerView) {
        ExploreDetailList = new ArrayList<>();
        //categoryList=catagory;
        this.onClick_recyclerView=mOnClick_recyclerView;

    }

    public SellerIdAdpater(ArrayList<SellerIdDetail> productList, Seller_id_detailActivity mOnClick_recyclerView) {
        ExploreDetailList = new ArrayList<>();
        //categoryList=catagory;
        this.onClick_recyclerView=mOnClick_recyclerView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_seller_id, parent, false);
        return new MyViewHolder(itemView,onClick_recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindView();
    }

    @Override
    public int getItemCount() {
        return ExploreDetailList.size();
    }

    public void update(ArrayList<SellerIdDetail> productList) {

        ExploreDetailList.addAll(productList);

        notifyDataSetChanged();
    }

    public interface OnClick_RecyclerVieww {
        void onAdClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //ImageView imageView;
        TextView title,numberOfPics;
        TextView price,catagory;

        OnClick_RecyclerVieww onClick_ViewHolder;

        MyViewHolder(View itemView,OnClick_RecyclerVieww onClick_recyclerView) {
            super(itemView);
            this.onClick_ViewHolder=onClick_recyclerView;
          //  imageView = itemView.findViewById(R.id.ivImage);
            title = itemView.findViewById(R.id.tv_seller_id_title);
           // catagory = itemView.findViewById(R.id.tv_seller_id_price);
            //numberOfPics = itemView.findViewById(R.id.tvNumberOfPics);
            price = itemView.findViewById(R.id.tv_seller_id_price);

            itemView.setOnClickListener(this);
        }

        void bindView() {

            title.setText("Bidder Name: "+ExploreDetailList.get(getAdapterPosition()).getUsername());
            price.setText("Price Offered: "+ExploreDetailList.get(getAdapterPosition()).getBidAmount());
            //catagory.setText("Posted Add In: "+categoryList.get(getAdapterPosition()));

           // numberOfPics.setText("Total Pictures: "+ExploreDetailList.get(getAdapterPosition()).getDownload_url().size());

        }

        @Override
        public void onClick(View v) {
            onClick_ViewHolder.onAdClick(getAdapterPosition());
        }
    }
}
