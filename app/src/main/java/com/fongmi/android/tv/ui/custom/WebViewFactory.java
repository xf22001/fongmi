package com.fongmi.android.tv.ui.custom;

import android.content.Context;
import android.util.Log;

import com.fongmi.android.tv.App;
import com.fongmi.android.tv.Setting;
import com.fongmi.android.tv.impl.IWebView;
import com.fongmi.android.tv.utils.Notify;
import com.fongmi.android.tv.utils.X5Initializer;

public class WebViewFactory {

    public interface Callback {
        void onCreated(IWebView webView);
    }

    public static void create(Context context, Callback callback) {
        String sniffer = Setting.getSniffer();
        if (sniffer.equals("x5")) {
            createX5(context, callback);
        } else {
            createSystem(context, callback);
        }
    }

    private static void createX5(Context context, Callback callback) {
        App.post(() -> {
            X5Initializer.init(context, success -> {
                App.post(() -> {
                    if (success) {
                        Log.d("WebViewFactory", "X5 core loaded, creating X5WebViewWrapper.");
                        callback.onCreated(new X5WebViewWrapper(context));
                    } else {
                        Log.w("WebViewFactory", "User selected X5 core, but it failed to initialize. Falling back to System WebView.");
                        Notify.show("X5核心加载失败，已回退到系统核心");
                        callback.onCreated(new SystemWebViewWrapper(context));
                    }
                });
            });
        });
    }

    private static void createSystem(Context context, Callback callback) {
        App.post(() -> {
            Log.d("WebViewFactory", "User selected System core, creating SystemWebViewWrapper.");
            callback.onCreated(new SystemWebViewWrapper(context));
        });
    }
}