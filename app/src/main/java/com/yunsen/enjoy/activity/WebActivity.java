package com.yunsen.enjoy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
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

import com.yunsen.enjoy.R;
import com.yunsen.enjoy.common.Constants;
import com.yunsen.enjoy.utils.WebUitls;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends BaseFragmentActivity {

    private static final String TAG = "WebActivity";
    @Bind(R.id.action_back)
    ImageView actionBack;
    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;
    @Bind(R.id.action_bar_right)
    ImageView actionBarRight;
    @Bind(R.id.webProgress)
    ProgressBar webProgress;
    @Bind(R.id.webRootView)
    FrameLayout webRootView;
    @Bind(R.id.web_error_layout)
    LinearLayout webErrorLayout;
    @Bind(R.id.web_title)
    TextView webTitle;
    @Bind(R.id.web_time)
    TextView webTime;
    @Bind(R.id.web_click_tv)
    TextView webClickTv;
    @Bind(R.id.web_category_tv)
    TextView webCategoryTv;
    @Bind(R.id.top_layout)
    LinearLayout topLayout;
    private WebView webView;
    private String mUrl = "";
    private boolean mWebError = false;

    private String mWebTitle;
    private String mWebClick;
    private String mWebTime;
    private String mWebCategory;
    private String mNoChangeTitle;

    @Override
    public int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        webView = new WebView(this);
        webRootView.addView(webView);
        webView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if(TextUtils.isEmpty(mNoChangeTitle)){
            actionBarTitle.setText("加载中，请稍后...");
        }

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mUrl = intent.getStringExtra(Constants.WEB_URL_KEY);
            mWebTitle = intent.getStringExtra(Constants.WEB_TITLE_KEY);
            mWebClick = intent.getStringExtra(Constants.WEB_CLICK_KEY);
            mWebTime = intent.getStringExtra(Constants.WEB_TIME_KEY);
            mWebCategory = intent.getStringExtra(Constants.WEB_CATEGORY_KEY);
            mNoChangeTitle = intent.getStringExtra(Constants.WEB_NO_CHANGE_TITLE);
        }
        if (!TextUtils.isEmpty(mWebTitle)) {
            webTitle.setText(mWebTitle);
            webCategoryTv.setText("分类: " + mWebCategory);
            webTime.setText("" + mWebTime);
            webClickTv.setText(mWebClick + "次浏览");
        }
        if (!TextUtils.isEmpty(mNoChangeTitle)) {
            actionBarTitle.setText(mNoChangeTitle);
        }

        WebUitls.initWebView(webView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mWebError = false;
                webProgress.setVisibility(View.VISIBLE);
                if (webView != null) {
                    webView.getSettings().setBlockNetworkImage(true);
                }
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webView == null) {
                    return;
                }
                if (!mWebError) {
                    webErrorLayout.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(mWebTitle)) {
                        topLayout.setVisibility(View.VISIBLE);
                    } else {
                        topLayout.setVisibility(View.GONE);
                    }
                } else {
                    topLayout.setVisibility(View.GONE);
                }
                webView.getSettings().setBlockNetworkImage(false);
                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                    //设置wenView加载图片资源
                    webView.getSettings().setBlockNetworkImage(false);
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }
                Log.e(TAG, "onPageFinished: " + url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //                String data = "页面未找到！";
                //                view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
                Log.e(TAG, "onReceivedError: " + failingUrl);
                mWebError = true;
                webErrorLayout.setVisibility(View.VISIBLE);
            }


        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (webProgress != null) {
                    if (newProgress > 90) {
                        webProgress.setVisibility(View.INVISIBLE);
                    } else {
                        webProgress.setVisibility(View.VISIBLE);
                        webProgress.setProgress(newProgress);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title != null && actionBarTitle != null) {
                    if (TextUtils.isEmpty(mNoChangeTitle)) {
                        actionBarTitle.setText(title);
                    }
                }
            }
        });
        //盖亚Homehttp://183.62.138.31:6060/mobile/default.html
        webView.loadUrl(mUrl);
    }

    @Override
    protected void initListener() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView != null && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webRootView.removeView(webView);
            webView = null;
        }
    }


    @OnClick(R.id.action_back)
    public void onViewClicked() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
}
