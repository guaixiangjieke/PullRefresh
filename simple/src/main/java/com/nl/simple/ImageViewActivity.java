package com.nl.simple;

import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;

public class ImageViewActivity extends RefreshActivity {
    @Override
    protected View generateView() {
        AppCompatImageView imageView = new AppCompatImageView(this){
            @Override
            public boolean canScrollHorizontally(int direction) {
                return !isChecked;
            }

            @Override
            public boolean canScrollVertically(int direction) {
                return isChecked;
            }
        };
        imageView.setClickable(true);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setBackgroundColor(Color.GREEN);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }
    public static final String TAG = ImageViewActivity.class.getSimpleName();
}
