package com.nl.simple;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends RefreshActivity {
    @Override
    protected View generateView() {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            views.add(imageView);
        }
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(new ViewPagerAdapter(views));
        return viewPager;
    }
}
