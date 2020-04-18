package com.sellanddonate.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.sellanddonate.app.util.ImageLoadingUtils;

import java.util.ArrayList;
//import com.squareup.picasso.Picasso;


public class ExploreDetailAddAdapter extends PagerAdapter {
    private Context context;
   // private String[] imageUrls;
    private ArrayList<String> imageUrls;

    public ExploreDetailAddAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
       // Toast.makeText(this.context,this.imageUrls.get(0),Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
//        Picasso.get()
//                .load(imageUrls[position])
//                .fit()
//                .centerCrop()
//                .into(imageView);
//        container.addView(imageView);
        ImageLoadingUtils
                .loadImage(context
                        , imageUrls.get(position)
                        , imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

