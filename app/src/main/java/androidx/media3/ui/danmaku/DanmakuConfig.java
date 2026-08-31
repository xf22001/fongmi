package androidx.media3.ui.danmaku;

public class DanmakuConfig {
    public static final int STYLE_NONE = 0;
    public static final int STYLE_STROKE = 1;
    public static final int STYLE_SHADOW = 2;
    public static final int STYLE_PROJECTION = 3;
    
    public static final int COLOR_MODE_DEFAULT = 0;
    public static final int COLOR_MODE_COLORFUL = 1;
    public static final int COLOR_MODE_GRADIENT = 2;

    public static final DanmakuConfig DEFAULT = new DanmakuConfig();

    public float textScale = 1.0f;
    public float transparency = 0.0f;
    public boolean textBold = false;
    public int styleMode = STYLE_STROKE;
    public float shadowTransparency = 0.1f;
    public float strokeWidthMultiplier = 0.12f;
    public float projectionOffsetXMultiplier = 0.08f;
    public float projectionOffsetYMultiplier = 0.08f;
    public float projectionTransparency = 0.2f;
    public int colorMode = COLOR_MODE_DEFAULT;
    public android.graphics.Typeface typeface = null;

    public int durationMs = 8000;
    public int fixedDurationMs = 8000;
    public int timeOffsetMs = 0;
    public int maxOnScreen = 0;
    public float scrollAreaRatio = 1.0f;
    public float scrollGapRatio = 0.5f;
    public float lineSpacing = 1.0f;
    public int maxScrollLines = 0;
    public int maxTopLines = 0;
    public int maxBottomLines = 0;
    public boolean showScroll = true;
    public boolean showTop = true;
    public boolean showBottom = true;
    public boolean showReverse = true;
    public boolean showPositioned = true;
    public boolean showSubtitle = true;
    public boolean showSpecial = true;

    public static class Builder {
        public Builder setTextScale(float scale) { return this; }
        public Builder setTransparency(float val) { return this; }
        public Builder setTextBold(boolean val) { return this; }
        public Builder setTypeface(android.graphics.Typeface val) { return this; }
        public Builder setStyleMode(int val) { return this; }
        public Builder setShadowTransparency(float val) { return this; }
        public Builder setStrokeWidthMultiplier(float val) { return this; }
        public Builder setProjectionOffsetXMultiplier(float val) { return this; }
        public Builder setProjectionOffsetYMultiplier(float val) { return this; }
        public Builder setProjectionTransparency(float val) { return this; }
        public Builder setColorMode(int val) { return this; }
        public Builder setDurationMs(long val) { return this; }
        public Builder setFixedDurationMs(long val) { return this; }
        public Builder setTimeOffsetMs(long val) { return this; }
        public Builder setMaxOnScreen(int val) { return this; }
        public Builder setScrollAreaRatio(float val) { return this; }
        public Builder setScrollGapRatio(float val) { return this; }
        public Builder setLineSpacing(float val) { return this; }
        public Builder setMaxScrollLines(int val) { return this; }
        public Builder setMaxTopLines(int val) { return this; }
        public Builder setMaxBottomLines(int val) { return this; }
        public Builder setShowScroll(boolean val) { return this; }
        public Builder setShowTop(boolean val) { return this; }
        public Builder setShowBottom(boolean val) { return this; }
        public Builder setShowReverse(boolean val) { return this; }
        public Builder setShowPositioned(boolean val) { return this; }
        public Builder setShowSubtitle(boolean val) { return this; }
        public Builder setShowSpecial(boolean val) { return this; }
        public DanmakuConfig build() { return DEFAULT; }
    }
}
