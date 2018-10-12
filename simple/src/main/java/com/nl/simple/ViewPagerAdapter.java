package com.nl.simple;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by NiuLei on 2018/2/22.
 * ViewPager适配器
 */

public class ViewPagerAdapter extends android.support.v4.view.PagerAdapter {
    private List<View> views;

    public ViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (views != null && !views.isEmpty() && position >= 0 && position < views.size()) {
            View view = views.get(position);
            if (view != null && view.getTag() != null) {
                Object tag = view.getTag();
                return tag.toString();
            }
        }
        return super.getPageTitle(position);
    }
}
