package com.nl.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.nl.recyclerview.OnItemPressListener;

import java.util.Arrays;

/**
 * 滚动嵌套?
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Class[] views = {View.class, ScrollView.class, HorizontalScrollView.class,
            RecyclerView.class, ViewPager.class, ImageView.class, WebView.class, ListView.class};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        SimpleRecyclerAdapter simpleRecyclerAdapter = new SimpleRecyclerAdapter(Arrays.asList(views));
        recyclerView.setAdapter(simpleRecyclerAdapter);
        recyclerView.addOnItemTouchListener(new OnItemPressListener() {
            @Override
            public void onItemPressed(RecyclerView recyclerView, RecyclerView.Adapter adapter,
                                      RecyclerView.ViewHolder viewHolder, int position) {
                Class view = views[position];
                Intent intent = null;
                if (view.equals(ImageView.class)) {
                    intent  = new Intent(MainActivity.this, ImageViewActivity.class);
                } else if (view.equals(RecyclerView.class)) {
                    intent  = new Intent(MainActivity.this, RecyclerViewActivity.class);
                }
                else if (view.equals(ScrollView.class)) {
                    intent  = new Intent(MainActivity.this, ScrollViewActivity.class);
                }else if (view.equals(HorizontalScrollView.class)) {
                    intent  = new Intent(MainActivity.this, HScrollViewActivity.class);
                }else if (view.equals(WebView.class)) {
                    intent  = new Intent(MainActivity.this, WebViewActivity.class);
                }else if (view.equals(ViewPager.class)) {
                    intent  = new Intent(MainActivity.this, ViewPagerActivity.class);
                }else {
                    intent  = new Intent(MainActivity.this, ViewActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                }

            }
        });
    }
}

