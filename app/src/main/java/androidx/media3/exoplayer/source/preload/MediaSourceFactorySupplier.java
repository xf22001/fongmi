package androidx.media3.exoplayer.source.preload;

import androidx.media3.datasource.cache.Cache;
import androidx.media3.datasource.DataSource;
import androidx.media3.exoplayer.source.MediaSource;

public interface MediaSourceFactorySupplier extends com.google.common.base.Supplier<MediaSource.Factory> {
    MediaSourceFactorySupplier setCache(Cache cache);
    MediaSourceFactorySupplier setDataSourceFactory(DataSource.Factory dataSourceFactory);
}
