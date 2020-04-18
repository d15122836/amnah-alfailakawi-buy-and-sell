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
import com.sellanddonate.app.util.ImageLoadingUtils;

import java.util.ArrayList;
import java.util.List;

public class MyAddsExlporeAdapter extends RecyclerView.Adapter<MyAddsExlporeAdapter.MyViewHolder> {

    private List<MyAdds> ExploreDetailList;
    private List<String> categoryList;
    OnClick_RecyclerVieww onClick_recyclerView;


    public MyAddsExlporeAdapter(List<MyAdds> exploreDetailList,List<String> catagory,OnClick_RecyclerVieww mOnClick_recyclerView) {
        ExploreDetailList = new ArrayList<>();
        categoryList=catagory;
        this.onClick_recyclerView=mOnClick_recyclerView;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_my_adds, parent, false);
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

    public void update(ArrayList<MyAdds> productList,List<String> categoryList) {

        ExploreDetailList.addAll(productList);
        categoryList.addAll(categoryList);
        notifyDataSetChanged();
    }

    public interface OnClick_RecyclerVieww {
        void onAdClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView title,numberOfPics;
        TextView price,catagory;

        OnClick_RecyclerVieww onClick_ViewHolder;

        MyViewHolder(View itemView,OnClick_RecyclerVieww onClick_recyclerView) {
            super(itemView);
            this.onClick_ViewHolder=onClick_recyclerView;
            imageView = itemView.findViewById(R.id.ivImage);
            title = itemView.findViewById(R.id.tvTitle);
            catagory = itemView.findViewById(R.id.txt_category);
            numberOfPics = itemView.findViewById(R.id.tvNumberOfPics);
            price = itemView.findViewById(R.id.tvPrice);

            itemView.setOnClickListener(this);
        }

        void bindView() {
            // contex , url , imageView
            ImageLoadingUtils
                    .loadImage(itemView.getContext()
                            , String.valueOf(ExploreDetailList.get(getAdapterPosition()).getDownload_url().get(0))
                            , imageView);

            title.setText(ExploreDetailList.get(getAdapterPosition()).getTitle());
            price.setText("Price: "+ExploreDetailList.get(getAdapterPosition()).getPrice());
            catagory.setText("Posted Add In: "+categoryList.get(getAdapterPosition()));

            numberOfPics.setText("Total Pictures: "+ExploreDetailList.get(getAdapterPosition()).getDownload_url().size());

        }

        @Override
        public void onClick(View v) {
            onClick_ViewHolder.onAdClick(getAdapterPosition());
        }
    }
}
