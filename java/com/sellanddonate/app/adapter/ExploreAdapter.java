package com.sellanddonate.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sellanddonate.app.R;
import com.sellanddonate.app.firebase.SellDetails;
import com.sellanddonate.app.util.ImageLoadingUtils;
import com.sellanddonate.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.MyViewHolder> {

    private List<SellDetails> ExploreDetailList;
    OnClick_RecyclerView onClick_recyclerView;

    public ExploreAdapter(List<SellDetails> exploreDetailList, OnClick_RecyclerView mOnClick_recyclerView) {
        ExploreDetailList = new ArrayList<>();
        this.onClick_recyclerView=mOnClick_recyclerView;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_explore, parent, false);
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

    public void update(ArrayList<SellDetails> productList) {

        ExploreDetailList.addAll(productList);
        notifyDataSetChanged();
    }

    public interface OnClick_RecyclerView {
        void onAddClick(int position,int type_adapter);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView title,numberOfPics,high_bid;
        TextView price,status;
        Button b_seller;

        OnClick_RecyclerView onClick_ViewHolder;

        MyViewHolder(View itemView,OnClick_RecyclerView onClick_recyclerView) {
            super(itemView);
            this.onClick_ViewHolder=onClick_recyclerView;
            imageView = itemView.findViewById(R.id.ivImage);
            b_seller=itemView.findViewById(R.id.seller_button);
            title = itemView.findViewById(R.id.tvTitle);
            numberOfPics = itemView.findViewById(R.id.tvNumberOfPics);
            price = itemView.findViewById(R.id.tvPrice);
            status = itemView.findViewById(R.id.tv_status);
            high_bid = itemView.findViewById(R.id.tv_high_bid);

            itemView.setOnClickListener(this);
        }

        void bindView() {
            // contex , url , imageView
            ImageLoadingUtils
                    .loadImage(itemView.getContext()
                            , String.valueOf(ExploreDetailList.get(getAdapterPosition()).getDownload_url().get(0))
                            , imageView);

            title.setText(ExploreDetailList.get(getAdapterPosition()).getTitle());
            high_bid.setText("Highest Bid: "+ExploreDetailList.get(getAdapterPosition()).getBid());
            price.setText("Price: "+ExploreDetailList.get(getAdapterPosition()).getPrice());
            if(ExploreDetailList.get(getAdapterPosition()).isAvailable()){
                status.setText("Status: Available");
            }else{
                status.setText("Status: Sold");
            }
            price.setText("Price: "+ExploreDetailList.get(getAdapterPosition()).getPrice());

            numberOfPics.setText("Total Pictures: "+ExploreDetailList.get(getAdapterPosition()).getDownload_url().size());

            b_seller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // ToastUtil.showToast("click "+getAdapterPosition());
                    onClick_recyclerView.onAddClick(getAdapterPosition(),2);
                }
            });
        }

        @Override
        public void onClick(View v) {
            onClick_ViewHolder.onAddClick(getAdapterPosition(),1);
        }
    }
}
