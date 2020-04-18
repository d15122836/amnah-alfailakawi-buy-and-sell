package com.sellanddonate.app.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sellanddonate.app.R;
import com.sellanddonate.app.firebase.BidDetail;
import com.sellanddonate.app.firebase.MyAdds;
import com.sellanddonate.app.firebase.historyDetail;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<BidDetail> ExploreDetailList;
    private List<historyDetail> categoryList;
   // OnClick_RecyclerVieww onClick_recyclerView;


    public HistoryAdapter(List<historyDetail> exploreDetailList, List<String> catagory) {
        categoryList = new ArrayList<>();
       // categoryList=catagory;


    }
    public HistoryAdapter(List<historyDetail> exploreDetailList) {
        categoryList  = new ArrayList<>();



    }

    public HistoryAdapter(ArrayList<String> catogoryList) {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_history, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindView();
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void update(ArrayList<historyDetail> productList) {

        categoryList.addAll(productList);

        notifyDataSetChanged();
    }

    public interface OnClick_RecyclerVieww {
        void onAdClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {
        ImageView imageView;
        TextView title;
//                ,numberOfPics;
//        TextView price,catagory;

       // OnClick_RecyclerVieww onClick_ViewHolder;

        MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_history);

        }

        void bindView() {
            // contex , url , imageView
//            ImageLoadingUtils
//                    .loadImage(itemView.getContext()
//                            , String.valueOf(ExploreDetailList.get(getAdapterPosition()).getDownload_url().get(0))
//                            , imageView);

            title.setText(" "+categoryList.get(getAdapterPosition()).getSold());



        }

      //  @Override
      //  public void onClick(View v) {
       //     onClick_ViewHolder.onAdClick(getAdapterPosition());
       // }
    }
}
