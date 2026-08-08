package com.fongmi.android.tv.player.effect.audio;

public final class AudioChannelMix {

    public static float mixStereoLeft(float[] samples) {
        if (samples.length == 0) return 0.0f;
        if (samples.length == 1) return samples[0];
        if (samples.length >= 6) {
            return samples[0] + 0.707f * samples[2] + 0.707f * samples[4];
        }
        return samples[0];
    }

    public static float mixStereoRight(float[] samples) {
        if (samples.length == 0) return 0.0f;
        if (samples.length == 1) return samples[0];
        if (samples.length >= 6) {
            return samples[1] + 0.707f * samples[2] + 0.707f * samples[5];
        }
        return samples[1];
    }

    public static float mixMono(float[] samples) {
        if (samples.length == 0) return 0.0f;
        float sum = 0.0f;
        for (float sample : samples) {
            sum += sample;
        }
        return sum / samples.length;
    }
}
