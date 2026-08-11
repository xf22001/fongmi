package androidx.media3.exoplayer.source.preload;

import android.net.Uri;
import androidx.media3.common.MediaItem;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.cache.Cache;
import androidx.media3.datasource.cache.CacheDataSource;
import androidx.media3.datasource.cache.CacheWriter;
import androidx.media3.exoplayer.ExoPlayer;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiskPreloadManager {

    private final Cache cache;
    private final androidx.media3.datasource.DataSource.Factory dataSourceFactory;
    private final ExecutorService executor;

    public static class Options {
        private long durationMs = 5000;
        private int threads = 1;
        public static class Builder {
            private final Options options = new Options();
            public Builder setDurationMs(long durationMs) { options.durationMs = durationMs; return this; }
            public Builder setMaxThreads(int threads) { options.threads = threads; return this; }
            public Options build() { return options; }
        }
        public static Builder builder() { return new Builder(); }
    }

    public static class Builder {
        private final Cache cache;
        private final androidx.media3.datasource.DataSource.Factory dataSourceFactory;
        public Builder(Cache cache, androidx.media3.datasource.DataSource.Factory dataSourceFactory, Object renderersFactory) {
            this.cache = cache;
            this.dataSourceFactory = dataSourceFactory;
        }
        public Builder setPriorityTaskManager(Object priorityTaskManager) { return this; }
        public DiskPreloadManager build() {
            return new DiskPreloadManager(cache, dataSourceFactory);
        }
    }

    private DiskPreloadManager(Cache cache, androidx.media3.datasource.DataSource.Factory dataSourceFactory) {
        this.cache = cache;
        this.dataSourceFactory = dataSourceFactory;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void start(ExoPlayer player, MediaItem mediaItem, Options options) {
        if (mediaItem.localConfiguration == null) return;
        Uri uri = mediaItem.localConfiguration.uri;
        executor.submit(() -> {
            try {
                // Physically preload 2MB of the video stream
                long preloadSize = 2 * 1024 * 1024; 
                DataSpec dataSpec = new DataSpec(uri, 0, preloadSize);
                CacheDataSource cacheDataSource = new CacheDataSource(cache, dataSourceFactory.createDataSource());
                CacheWriter cacheWriter = new CacheWriter(cacheDataSource, dataSpec, null, null);
                cacheWriter.cache();
            } catch (Exception e) {
                // Safely ignore preloading exceptions in background
            }
        });
    }

    public void add(MediaItem mediaItem) {}
    public void remove(MediaItem mediaItem) {}
    public void release() {
        executor.shutdown();
    }
}
