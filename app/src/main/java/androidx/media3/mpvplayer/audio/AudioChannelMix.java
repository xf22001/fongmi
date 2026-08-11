package androidx.media3.mpvplayer.audio;

public class AudioChannelMix {
    public AudioChannelMix(float[][] matrix) {}

    public static float[][] createFrontCenterGainMix(int channelCount, float factor) { return null; }
    public static float[][] createStereoMix(int channelCount, boolean reverse) { return null; }
    public static float[][] createMonoMix(int channelCount) { return null; }
    public static float[][] createFrontBalanceMix(int channelCount, float balance) { return null; }
    public static float[][] compose(float[][] m1, float[][] m2) { return null; }
}
