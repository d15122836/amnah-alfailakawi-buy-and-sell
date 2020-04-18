package com.sellanddonate.app.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoadingUtils {

    private static final String TAG = ImageLoadingUtils.class.getSimpleName();

    public static void loadImage(Context context, String url, ImageView imageView) {
        loadWithGlide(context, url, imageView);
    }

    //to get photo from storage use this funciton of glide
    private static void loadWithGlide(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

}
