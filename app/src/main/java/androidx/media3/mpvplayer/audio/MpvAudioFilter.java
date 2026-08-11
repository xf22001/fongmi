package androidx.media3.mpvplayer.audio;

public class MpvAudioFilter {
    public static final MpvAudioFilter EMPTY = new MpvAudioFilter("");

    public MpvAudioFilter(String filter) {}

    public static class Builder {
        public Builder() {}
        public Builder append(String filter) { return this; }
        public Builder append(AudioChannelMix mix) { return this; }
        public Builder addLoudnessNormalization(String label, double val1, double val2, double val3) { return this; }
        public Builder addCompressor(String label, float val1, float val2, float val3, float val4) { return this; }
        public Builder addVolume(String label, double val1) { return this; }
        public Builder addEqualizer(String label, int val1, double val2) { return this; }
        public Builder addLimiter(String label, double val1) { return this; }
        public Builder addRuntimeChannelMix(String label, float[][] mix) { return this; }
        public MpvAudioFilter build() { return EMPTY; }
    }
}
