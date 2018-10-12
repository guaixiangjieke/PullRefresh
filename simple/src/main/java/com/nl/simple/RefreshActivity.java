package com.nl.simple;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.nl.pullrefresh.PullRefreshLayout;

public abstract class RefreshActivity extends AppCompatActivity implements CompoundButton
        .OnCheckedChangeListener {
    private Handler handler = new Handler();
    private PullRefreshLayout mPullRefreshLayout;
    protected boolean isChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        mPullRefreshLayout = findViewById(R.id.mPullRefreshSimpleLayout);
        ToggleButton mToggleButton = findViewById(R.id.mToggleButton);
        mToggleButton.setOnCheckedChangeListener(this);
        View view = generateView();
        if (view == null) {
            finish();
            return;
        }
        mPullRefreshLayout.setContentView(view);
        mPullRefreshLayout.setOnPullRefreshListener(new PullRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onPullRefresh(int direction) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshLayout.setSuccess();
                    }
                }, 5000);
            }
        });
    }

    protected abstract View generateView();

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.isChecked = isChecked;
        mPullRefreshLayout.setOrientation(isChecked ? PullRefreshLayout.HORIZONTAL :
                PullRefreshLayout.VERTICAL);
    }
}
