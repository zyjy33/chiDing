package com.yunsen.enjoy.ui.loadmore;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;

/**
 * Created by Administrator on 2018/8/6/006.
 */

public class ZyBottomView extends AppCompatTextView implements IBottomView {
    public ZyBottomView(Context context) {
        this(context, null);
    }

    public ZyBottomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZyBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int size = DensityUtil.dp2px(context, 34);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size);
        params.gravity = Gravity.CENTER;
        setLayoutParams(params);
        this.setGravity(Gravity.CENTER);
        this.setText("没有更多数据");
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void reset() {

    }
}
