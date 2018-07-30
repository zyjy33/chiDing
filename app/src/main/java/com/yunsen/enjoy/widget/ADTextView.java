package com.yunsen.enjoy.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.model.NoticeModel;

import java.util.List;


/**
 * 文字上下滚动
 */

public class ADTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {


    private int index = -1;
    private Context context;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    index = next(); //取得下标值
                    updateText();  //更新TextSwitcherd显示内容;
                    sendEmptyMessageDelayed(1, mTime);
                    break;
                case 2:
                    index = next(); //取得下标值
                    updateText();  //更新TextSwitcherd显示内容;
                    sendEmptyMessageDelayed(2, mTime);
                    break;

            }
        }

        ;
    };

    private List<NoticeModel> resources;
    private long mTime;

    public ADTextView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ADTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        this.setFactory(this);
        this.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_from_bottom));
        this.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_from_top));
    }

    public void setResources(List<NoticeModel> res) {
        this.resources = res;
    }

    public void setTextStillTime(long time, int index, int eventId) {
        this.mTime = time;
        this.index = index;
        mHandler.sendEmptyMessageDelayed(eventId, 1);
    }


    private int next() {
        int flag = index + 1;
        if (flag > resources.size() - 1) {
            flag = flag - resources.size();
        }
        return flag;
    }

    private void updateText() {
        if (resources != null && resources.size() > index) {
            String title = resources.get(index).getTitle();
            if (title != null) {
                title = title.trim();
            }
            this.setText(title);
        }
    }

    @Override
    public View makeView() {
        TextView tv = new TextView(context);
        tv.setSingleLine();
        tv.setEllipsize(TextUtils.TruncateAt.END);
        FrameLayout.LayoutParams lp = new
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        tv.setLayoutParams(lp);
        return tv;
    }

    public void onStartAuto(int eventId) {
        if (resources == null) {
            return;
        }
        if (mHandler != null && !mHandler.hasMessages(eventId)) {
            mHandler.sendEmptyMessageDelayed(eventId, mTime);
        }
    }

    public void onStopAuto(int eventId) {
        if (mHandler != null && mHandler.hasMessages(eventId)) {
            mHandler.removeMessages(eventId);
        }
    }

    public NoticeModel getCurrentData() {
        if (resources != null && resources.size() > index) {
            return resources.get(index);
        } else {
            return null;
        }
    }

}
