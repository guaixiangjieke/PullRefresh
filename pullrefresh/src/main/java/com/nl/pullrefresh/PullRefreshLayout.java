package com.nl.pullrefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * 刷新布局基类
 *
 * @author NiuLei
 * @date 2018/9/21 10:14
 */
public abstract class PullRefreshLayout extends RelativeLayout {
    /**
     * 水平方向
     */
    public static final int HORIZONTAL = 0;
    /**
     * 垂直方向
     */
    public static final int VERTICAL = 1;

    /**
     * 负数 下拉 右拉
     */
    public static final int NEGATIVE = -1;
    /**
     * 正数 上拉 左拉
     */
    public static final int POSITIVE = 1;

    /**
     * 默认滚动持续时间
     */
    private static final int DEF_SCROLL_DURATION = 800;

    /**
     * 滚动持续时间
     */
    private int scrollDuration = DEF_SCROLL_DURATION;
    /**
     * 内容视图
     */
    private View contentView;
    /**
     * 内容视图资源id
     */
    private int contentViewId;
    /**
     * 滚动帮助类
     */
    private Scroller scroller;
    /**
     * 方向 默认水平
     * {@link #VERTICAL 垂直方向} {@link #HORIZONTAL 水平方向}
     */
    private int orientation = VERTICAL;
    /**
     * 头视图高度
     */
    private int headHeight;
    /**
     * 底部视图高度
     */
    private int footHeight;
    /**
     * 是否刷新中
     */
    private boolean isRefreshing;
    /**
     * 是否可以下拉 右拉
     */
    private boolean headAble = true;
    /**
     * 是否可以上拉 左拉
     */
    private boolean footAble = true;
    private View headView;
    private View footView;

    public PullRefreshLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PullRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PullRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable
                .PullRefreshLayout, defStyleAttr, 0);
        contentViewId = typedArray.getResourceId(R.styleable.PullRefreshLayout_contentViewId, -1);
        orientation = typedArray.getInt(R.styleable.PullRefreshLayout_orientation, VERTICAL);
        typedArray.recycle();
        scroller = new Scroller(context);
        onInit();
    }
    public void onInit(){

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (contentViewId != -1) {
            contentView = findViewById(contentViewId);
        }
        update();
    }

    private void update(){
        addHeadView();
        addFootView();
    }

    /**
     * 添加头视图
     */
    private void addHeadView() {
        if (headView != null) {
            removeView(headView);
            headView = null;
        }
        headView = getHeadView();
        if (null == headView) {
            return;
        }
        headView.measure(0, 0);
        int measuredWidth = headView.getMeasuredWidth();
        int measuredHeight = headView.getMeasuredHeight();
        LayoutParams headLayoutParams = new LayoutParams(measuredWidth, measuredHeight);
        if (HORIZONTAL == orientation) {
            headHeight = measuredWidth;
            headLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            headLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            headLayoutParams.leftMargin = -headHeight;
        } else {
            headHeight = measuredHeight;
            headLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            headLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            headLayoutParams.topMargin = -headHeight;
        }
        addView(headView,headLayoutParams);
    }

    /**
     * 添加底部加载视图
     */
    private void addFootView() {
        if (footView != null) {
            removeView(footView);
            footView = null;
        }
        footView = getFootView();
        if (footView == null) {
            return;
        }
        footView.measure(0, 0);
        int measuredWidth = footView.getMeasuredWidth();
        int measuredHeight = footView.getMeasuredHeight();

        LayoutParams footLayoutParams = new LayoutParams(measuredWidth, measuredHeight);
        if (HORIZONTAL == orientation) {
            footHeight = measuredWidth;
            footLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            footLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            footLayoutParams.rightMargin = -footHeight;
        } else {
            footHeight = measuredHeight;
            footLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            footLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            footLayoutParams.bottomMargin = -footHeight;
        }
        addView(footView,footLayoutParams);
    }

    /**
     * 获取头视图
     */
    protected abstract View getHeadView();
    /**
     * 获取底部视图
     */
    protected abstract View getFootView();

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (l == 0 && t == 0) {
            idle();
        }
    }


    private PointF downPoint = new PointF();
    private PointF diffPoint = new PointF();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        if (isRefreshing) {
            return b;
        }
        int action = ev.getAction();
        float x = ev.getX();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                downPoint.x = x;
                downPoint.y = y;
                break;
            case MotionEvent.ACTION_UP:
                dispatchEventUp();
                break;
            case MotionEvent.ACTION_MOVE:
                diffPoint.x = x - downPoint.x;
                diffPoint.y = y - downPoint.y;
                downPoint.x = x;
                downPoint.y = y;
                dispatchEventMove();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return b;
    }

    /**
     * 处理抬起事件
     */
    private void dispatchEventUp() {
        if (isRefreshing) {
            return;
        }
        if (HORIZONTAL == orientation) {/*水平方向*/
            int scrollX = getScrollX();
            if (scrollX > 0 && scrollX >= footHeight || scrollX < 0 && Math.abs(scrollX) >=
                    headHeight) {
                refresh();
                return;
            }
        } else {/*垂直方向*/
            int scrollY = getScrollY();
            if (scrollY > 0 && scrollY >= footHeight || scrollY < 0 && Math.abs(scrollY) >=
                    headHeight) {
                refresh();
                return;
            }
        }
        setSuccess();
    }

    /**
     * 处理移动事件
     */
    private void dispatchEventMove() {
        if (isRefreshing) {
            return;
        }
        if (HORIZONTAL == orientation) {/*水平方向*/
            if (canPullHorizontally()) {/*可拉动*/
                /*左拉*/
                boolean isLeftPull = diffPoint.x < 0;
                float percent = 1f - Math.abs(getScrollX()) / (float) (isLeftPull ? footHeight :
                        headHeight);
                if (percent == 0) {
                    return;
                }
                float absX = Math.abs(diffPoint.x) * percent;
                /*精度损失处理*/
                int scrollByX = (int) (absX < 1 ? 1 : absX);
                scrollBy(isLeftPull ? (footAble ? scrollByX : 0) : (headAble ? -scrollByX : 0), 0);
            }
        } else {/*垂直方向*/
            if (canPullVertically()) {/*垂直方向可拉动*/
                /*上拉*/
                boolean isTopPull = diffPoint.y < 0;
                float percent = 1f - Math.abs(getScrollY()) / (float) (isTopPull ? footHeight :
                        headHeight);
                if (percent == 0) {
                    return;
                }
                float absY = Math.abs(diffPoint.y) * percent;
                /*精度损失处理*/
                int scrollByY = (int) (absY < 1 ? 1 : absY);
                scrollBy(0, isTopPull ? (footAble ? scrollByY : 0) : (headAble ? -scrollByY : 0));
            }
        }
    }

    /**
     * 检查垂直方向是否可以拉动
     */
    public boolean canPullVertically() {
        if (contentView == null) {
            return false;
        }
        boolean canScrollTop = contentView.canScrollVertically(POSITIVE);
        boolean canScrollDown = contentView.canScrollVertically(NEGATIVE);
        return diffPoint.y > 0 && !canScrollDown || diffPoint.y < 0 && !canScrollTop;
    }

    /**
     * 检查水平方向是否可以拉动
     */
    private boolean canPullHorizontally() {
        if (contentView == null) {
            return false;
        }
        boolean canScrollLeft = contentView.canScrollHorizontally(POSITIVE);
        boolean canScrollRight = contentView.canScrollHorizontally(NEGATIVE);
        return diffPoint.x > 0 && !canScrollRight || diffPoint.x < 0 && !canScrollLeft;
    }


    /**
     * 平滑滚动到目标位置
     *
     * @param x
     * @param y
     */
    public void smoothScrollTo(int x, int y) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        scroller.startScroll(scrollX, scrollY, x - scrollX, y - scrollY, scrollDuration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 设置内容视图
     * @param contentView 内容视图
     */
    public void setContentView(View contentView) {
        if (this.contentView != null) {
            removeView(this.contentView);
            this.contentView = null;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams
                .MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(contentView,layoutParams);
        this.contentView = contentView;
    }

    public boolean isHeadAble() {
        return headAble;
    }

    public void setHeadAble(boolean headAble) {
        this.headAble = headAble;
    }

    public boolean isFootAble() {
        return footAble;
    }

    public void setFootAble(boolean footAble) {
        this.footAble = footAble;
    }

    public int getScrollDuration() {
        return scrollDuration;
    }

    public void setScrollDuration(int scrollDuration) {
        this.scrollDuration = scrollDuration;
    }

    /**
     * 设置拉动方向
     *
     * @param orientation {@link #VERTICAL 垂直方向} {@link #HORIZONTAL 水平方向}
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
        update();
    }

    public int getOrientation() {
        return orientation;
    }

    /**
     * 刷新
     */
    public void refresh() {
        isRefreshing = true;
        if (onPullRefreshListener != null) {
            onPullRefreshListener.onPullRefresh(HORIZONTAL == orientation ?
                    getHorizontalDirection() : getVerticalDirection());
        }
        onRefresh();
    }

    /**
     * 空闲状态
     */
    private void idle() {
        isRefreshing = false;
        onIdle();
    }

    /**
     * 获取垂直方向
     *
     * @return
     */
    private int getVerticalDirection() {
        return getScrollY() > 0 ? POSITIVE : NEGATIVE;
    }

    /**
     * 获取水平方向
     */
    private int getHorizontalDirection() {
        return getScrollX() > 0 ? POSITIVE : NEGATIVE;
    }

    /**
     * 刷新回调
     */
    public void onRefresh() {

    }

    /**
     * 空闲回调
     */
    public void onIdle() {

    }

    /**
     * 是否正在刷新
     */
    public boolean isRefreshing() {
        return isRefreshing;
    }

    /**
     * 刷新完成 、释放
     */
    public void setSuccess() {
        smoothScrollTo(0, 0);
    }

    public int getHeadHeight() {
        return headHeight;
    }

    public int getFootHeight() {
        return footHeight;
    }

    /**
     * 拉动监听
     */
    public interface OnPullRefreshListener {
        /**
         * 刷新回调
         *
         * @param direction 方向 {@link #NEGATIVE } {@link #POSITIVE 水平方向}
         */
        void onPullRefresh(int direction);
    }

    private OnPullRefreshListener onPullRefreshListener;

    /**
     * 设置拉动监听
     *
     * @param onPullRefreshListener
     */
    public void setOnPullRefreshListener(OnPullRefreshListener onPullRefreshListener) {
        this.onPullRefreshListener = onPullRefreshListener;
    }
}
