package com.fongmi.android.tv.impl;

import android.view.View;
import java.util.Map;

public interface IWebView {
    View getView();
    IWebView start(String key, String from, Map<String, String> headers, String url, String click, ParseCallback callback, boolean detect);
    void stop(boolean error);
    void destroy();
}
