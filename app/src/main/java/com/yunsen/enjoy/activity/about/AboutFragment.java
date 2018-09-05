package com.yunsen.enjoy.activity.about;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.fragment.BaseFragment;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.utils.DeviceUtil;
import com.yunsen.enjoy.utils.WebUitls;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/31/031.
 */

public class AboutFragment extends BaseFragment {
    private static final String TAG = "AboutFragment";
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.root_layout)
    LinearLayout webRootView;
    @Bind(R.id.action_back_layout)
    LinearLayout actionBackLayout;
    @Bind(R.id.web_error_layout)
    LinearLayout webErrorLayout;
    private WebView webView;
    @Bind(R.id.webProgress)
    ProgressBar webProgress;
    @Bind(R.id.about_img)
    ImageView aboutImg;
    private boolean mWebError;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this, rootView);
        actionBack.setVisibility(View.GONE);
        actionBarRight.setVisibility(View.GONE);
        actionBackLayout.setBackground(null);
        actionBarTitle.setGravity(Gravity.CENTER);
        actionBarTitle.setText("关于我们");
        actionBarTitle.setTextColor(getResources().getColor(R.color.white));

//        aboutImg.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        aboutImg.setBackgroundResource(R.mipmap.about_img);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            aboutImg.post(new Runnable() {
                @Override
                public void run() {
                    int screenWidth = DeviceUtil.getScreenWidth();
                    int height = screenWidth / 793 * 3200;
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) aboutImg.getLayoutParams();
                    params.height = height;
                    params.width = screenWidth;
                    aboutImg.requestLayout();
                }
            });
        }


//        webView = new WebView(getActivity());
//        webRootView.addView(webView);
//        webView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//        WebUitls.initWebView(webView);
//
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                mWebError = false;
//                webProgress.setVisibility(View.VISIBLE);
//                if (webView != null) {
//                    webView.getSettings().setBlockNetworkImage(true);
//                }
//            }
//
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                if (webView == null) {
//                    return;
//                }
//                if (!mWebError) {
//                    webErrorLayout.setVisibility(View.GONE);
//                    webRootView.setVisibility(View.VISIBLE);
//                } else {
//                    webErrorLayout.setVisibility(View.VISIBLE);
//                    webRootView.setVisibility(View.GONE);
//                }
//                webView.getSettings().setBlockNetworkImage(false);
//                if (!webView.getSettings().getLoadsImagesAutomatically()) {
//                    //设置wenView加载图片资源
//                    webView.getSettings().setBlockNetworkImage(false);
//                    webView.getSettings().setLoadsImagesAutomatically(true);
//                }
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();
//            }
//
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                //                String data = "页面未找到！";
//                //                view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
//                Log.e(TAG, "onReceivedError: " + failingUrl);
//                mWebError = true;
//                if (webErrorLayout != null) {
//                    webErrorLayout.setVisibility(View.VISIBLE);
//                }
//            }
//
//
//        });
//
//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (webProgress != null) {
//                    if (newProgress > 90) {
//                        webProgress.setVisibility(View.INVISIBLE);
//                    } else {
//                        webProgress.setVisibility(View.VISIBLE);
//                        webProgress.setProgress(newProgress);
//                    }
//                }
//                super.onProgressChanged(view, newProgress);
//            }
//
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//                if (title != null && actionBarTitle != null) {
////                    actionBarTitle.setText(title);
//                }
//            }
//
//        });
//        //盖亚Homehttp://183.62.138.31:6060/mobile/default.html
//        webView.loadUrl(URLConstants.ABOUT_URL);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void initListener() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webView != null) {
            webRootView.removeView(webView);
            webView = null;
        }
        ButterKnife.unbind(this);
    }
}
