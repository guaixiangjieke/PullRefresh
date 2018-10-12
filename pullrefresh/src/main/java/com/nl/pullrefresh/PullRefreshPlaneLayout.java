package com.nl.pullrefresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 纸飞机效果
 * @author NiuLei
 * @date 2018/9/25 15:14
 */
public class PullRefreshPlaneLayout extends PullRefreshLayout{
    public PullRefreshPlaneLayout(Context context) {
        super(context);
    }

    public PullRefreshPlaneLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PullRefreshPlaneLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View getHeadView() {
        return null;
    }

    @Override
    protected View getFootView() {
        return null;
    }
}
