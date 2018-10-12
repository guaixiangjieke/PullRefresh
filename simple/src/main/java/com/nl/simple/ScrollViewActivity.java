package com.nl.simple;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.nl.pullrefresh.PullRefreshLayout;
import com.nl.pullrefresh.PullRefreshSimpleLayout;

public class ScrollViewActivity extends AppCompatActivity {
    Handler handler = new Handler();
    private PullRefreshSimpleLayout mPullRefreshSimpleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        mPullRefreshSimpleLayout = findViewById(R.id.mPullRefreshSimpleLayout);
        mPullRefreshSimpleLayout.setOnPullRefreshListener(new PullRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onPullRefresh(int direction) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshSimpleLayout.setSuccess();
                    }
                }, 5000);
            }
        });
    }
}
