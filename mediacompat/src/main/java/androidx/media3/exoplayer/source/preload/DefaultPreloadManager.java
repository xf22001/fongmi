package androidx.media3.exoplayer.source.preload;

import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.LoadControl;
import androidx.media3.exoplayer.RenderersFactory;
import androidx.media3.exoplayer.source.MediaSource;

public class DefaultPreloadManager {

    public interface PreloadStatus {
        static PreloadStatus specifiedRangeLoaded(long startPositionMs, long durationMs) { return null; }
    }

    public interface StatusSupplier {
        PreloadStatus get(androidx.media3.common.MediaItem item);
    }

    public static class Builder {
        public Builder(android.content.Context context, StatusSupplier supplier) {}
        public Builder setMediaSourceFactorySupplier(com.google.common.base.Supplier<MediaSource.Factory> supplier) { return this; }
        public Builder setRenderersFactory(RenderersFactory factory) { return this; }
        public Builder setTrackSelectorFactory(Object factory) { return this; }
        public Builder setLoadControl(LoadControl loadControl) { return this; }
        public ExoPlayer buildExoPlayer(ExoPlayer.Builder builder) { return builder.build(); }
        public DefaultPreloadManager build() { return null; }
    }

    public void add(androidx.media3.common.MediaItem item, long startPositionMs) {}
    public void remove(androidx.media3.common.MediaItem item) {}
    public void invalidate() {}
    public void release() {}
    public void addListener(PreloadManagerListener listener) {}
    public MediaSource getMediaSource(androidx.media3.common.MediaItem item) { return null; }
}
