package com.nl.simple;

import android.view.View;
import android.webkit.WebView;

public class WebViewActivity extends RefreshActivity {
    @Override
    protected View generateView() {
        WebView webView = new WebView(this);
        webView.loadUrl("https://www.163.com/");
        return webView;
    }
}
