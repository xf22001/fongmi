package androidx.media3.mpvplayer.video;

public class MpvVideoEqualizer {
    public static final MpvVideoEqualizer DEFAULT = new MpvVideoEqualizer(0, 0, 0, 0, 0);

    public MpvVideoEqualizer(int brightness, int contrast, int saturation, int gamma, int hue) {}

    public static MpvVideoEqualizer create(float brightness, float contrast, float saturation, float gamma, float hue, float sharpness) {
        return DEFAULT;
    }
}
