package com.rongfeng.speedclient.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 意见反馈
 * 2016/1/12
 */
public class RebackActivity extends BaseActivity {


    @Bind(R.id.web_view)
    WebView mWebView;
    @Bind(R.id.cancel_tv)
    ImageView cancelTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tencent_reback_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://59.110.52.231:8108/qidian/qidian_web.html");
        mWebView.setWebViewClient(new DemoWebViewClient());

    }

    @OnClick(R.id.cancel_tv)
    public void onClick() {
        finish();
    }

    class DemoWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
}
