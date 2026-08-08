package com.fongmi.android.tv.ui.custom;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.ui.PlayerView;
import androidx.media3.ui.danmaku.DanmakuConfig;
import okhttp3.OkHttpClient;

public class CustomPlayerView extends PlayerView {

    public CustomPlayerView(@NonNull Context context) {
        super(context);
    }

    public CustomPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRender(int render) {
    }

    public void setDanmakuOkHttpClient(OkHttpClient client) {
    }

    public void setDanmakuEnabled(boolean enabled) {
    }

    public void setDanmakuConfig(DanmakuConfig config) {
    }

    public void setDanmakuSource(Uri uri) {
    }

    public boolean isDebugViewVisible() {
        return false;
    }

    public void toggleDebugView() {
    }

    public void sendDanmaku(String text) {
    }
}
