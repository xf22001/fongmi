package androidx.media3.mpvplayer;
import java.io.File;
public class MpvPlayerConfig {
    public static class Builder {
        public Builder addConfigDirectory(File dir) { return this; }
        public Builder addAndroidFontConfig(File dir, File cacheDir) { return this; }
        public Builder addAndroidDefaults(MpvAndroidOptions options) { return this; }
        public Builder addTlsCaFileFromAsset(android.content.Context context, String asset, File target) { return this; }
        public Builder addAndroidSubtitleOptions(android.content.Context context, MpvSubtitleOptions options) { return this; }
        public Builder addDiskCacheOptions(File cacheDir, int timeSeconds) { return this; }
        public MpvPlayerConfig build() { return null; }
    }
}
