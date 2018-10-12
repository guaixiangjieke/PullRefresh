package com.nl.pullrefresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 类似{@link android.support.v4.widget.SwipeRefreshLayout}效果
 * @author NiuLei
 * @date 2018/9/25 15:14
 */
public class PullRefreshSwipeLayout extends PullRefreshLayout{
    public PullRefreshSwipeLayout(Context context) {
        super(context);
    }

    public PullRefreshSwipeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PullRefreshSwipeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
