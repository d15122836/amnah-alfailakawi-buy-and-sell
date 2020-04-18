package com.sellanddonate.app.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sellanddonate.app.R;
import com.sellanddonate.app.util.ImageLoadingUtils;

import java.util.List;
//adapter is used to display image... of phothideacitivity
public class photosAdapter extends RecyclerView.Adapter<photosAdapter.MyViewHolder> {

    private List<String> photoList;

    public photosAdapter(List<String> photoList) {
        this.photoList = photoList;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_selected_photo_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.bindView();
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bindView() {
            ImageLoadingUtils.loadImage(itemView.getContext(), photoList.get(getAdapterPosition()), imageView);
        }
    }
}
