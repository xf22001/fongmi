package androidx.media3.mpvplayer;

public class MpvSubtitleOptions {
    public static class Builder {
        public Builder setPosition(double position) { return this; }
        public Builder setScale(double scale) { return this; }
        public Builder setSecondarySubtitle(int trackId, float position, boolean styleForced) { return this; }
        public Builder setSecondarySubtitlePosition(float position) { return this; }
        public Builder setSecondaryAssStyleOverride(boolean override) { return this; }
        public Builder setFontFamily(String fontFamily) { return this; }
        public Builder setFontsDirectory(String fontsDirectory) { return this; }
        public Builder setCustomStyle(int textColor, int backgroundColor, int edgeType, int edgeColor, float edgeWidth, float shadow) { return this; }
        public Builder setSystemCaptionStyle() { return this; }
        public MpvSubtitleOptions build() { return null; }
    }
}
