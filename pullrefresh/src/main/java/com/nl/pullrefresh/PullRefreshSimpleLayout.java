package com.nl.pullrefresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 常规上下拉组件
 *
 * @author NiuLei
 * @date 2018/9/25 15:13
 */
public class PullRefreshSimpleLayout extends PullRefreshLayout {
    /**
     * 刷新箭头
     */
    private ImageView simple_refresh_ic;
    /**
     * 刷新文本
     */
    private TextView simple_refresh_text;

    public PullRefreshSimpleLayout(Context context) {
        super(context);
    }

    public PullRefreshSimpleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PullRefreshSimpleLayout(Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View getHeadView() {
        if (HORIZONTAL == getOrientation()) {
            return getProgressBar();
        }
        View headView = LayoutInflater.from(getContext()).inflate(R.layout
                .simple_refresh_top_layout, null);
        simple_refresh_ic = headView.findViewById(R.id.simple_refresh_ic);
        simple_refresh_text = headView.findViewById(R.id.simple_refresh_text);
        return headView;
    }

    @Override
    protected View getFootView() {
        return getProgressBar();
    }

    @NonNull
    private ProgressBar getProgressBar() {
        ProgressBar progressBar = new ProgressBar(getContext());
        int dpFootPadding = getResources().getDimensionPixelSize(R.dimen
                .simple_refresh_foot_margin);
        progressBar.setPadding(dpFootPadding, dpFootPadding, dpFootPadding, dpFootPadding);
        return progressBar;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (simple_refresh_ic == null) {
            return;
        }
        simple_refresh_ic.setVisibility(GONE);
        simple_refresh_text.setText(getResources().getString(R.string.simple_refresh_text));
    }

    @Override
    public void onIdle() {
        super.onIdle();
        if (simple_refresh_ic == null) {
            return;
        }
        simple_refresh_ic.setVisibility(VISIBLE);
        simple_refresh_ic.setRotation(0);
        simple_refresh_text.setText(getResources().getString(R.string
                .simple_refresh_pull_down_text));
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (simple_refresh_ic == null) {
            return;
        }
        if (t < 0 && Math.abs(t) >= getHeadHeight()) {
            simple_refresh_text.setText(getResources().getString(R.string
                    .simple_refresh_release_text));
            simple_refresh_ic.setRotation(180);
        }
    }
}
