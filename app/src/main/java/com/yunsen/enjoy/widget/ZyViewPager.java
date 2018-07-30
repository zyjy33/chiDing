package com.yunsen.enjoy.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;

import com.yunsen.enjoy.utils.DeviceUtil;

/**
 * Created by Administrator on 2018/4/22.
 */

public class ZyViewPager extends ViewPager {

    /**
     * 触摸时按下的点
     **/
    PointF downP = new PointF();
    /**
     * 触摸时当前的点
     **/
    PointF curP = new PointF();

    /**
     * 自定义手势
     **/
    private GestureDetector mGestureDetector;
    private ViewGroup parent;
    private int mPosition = 0;

    public ZyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZyViewPager(Context context) {
        super(context);
    }

    public void setParent(ViewGroup parent) {
        this.parent = parent;
    }
    //
    //    @Override
    //    public boolean dispatchTouchEvent(MotionEvent ev) {
    //        if (parent != null) {
    //            Log.i("dispatch_touch_event", "---" + ev.getAction());
    //        }
    //        return super.dispatchTouchEvent(ev);
    //    }
    //
    //    @Override
    //    public boolean onInterceptTouchEvent(MotionEvent ev) {
    //        if (parent != null) {
    //            Log.i("onintercepte_touch_eve", "---" + ev.getAction());
    //        }
    //        return super.onInterceptTouchEvent(ev);
    //    }
    //
    //    @Override
    //    public boolean onTouchEvent(MotionEvent ev) {
    //        if (parent != null) {
    //            Log.i("on_touch_event", "---" + ev.getAction());
    //            parent.requestDisallowInterceptTouchEvent(true);
    //        }
    //        return super.onTouchEvent(ev);
    //    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            if (h > height)
//                height = h;
//        }
        if (mPosition < getChildCount()) {
            View child = getChildAt(mPosition);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height = child.getMeasuredHeight();
        }
        int pagerHeight = DeviceUtil.getScreenHeight() - DeviceUtil.dp2px(getContext(), 225);
        height = Math.max(pagerHeight, height);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void upViewPagerIndexHeight(int position) {
        this.mPosition = position;
        requestLayout();
    }

}
