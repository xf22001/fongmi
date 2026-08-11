package androidx.media3.mpvplayer;
import java.io.File;
public class MpvAndroidOptions {
    public static class Builder {
        public Builder setShaderCacheDirectory(File dir) { return this; }
        public Builder setAudioPassthroughEnabled(boolean enabled) { return this; }
        public Builder setDolbyVisionOutputPolicy(int policy) { return this; }
        public Builder setGpuNextEnabled(boolean enabled) { return this; }
        public Builder setVulkanEnabled(boolean enabled) { return this; }
        public MpvAndroidOptions build() { return null; }
    }
}
