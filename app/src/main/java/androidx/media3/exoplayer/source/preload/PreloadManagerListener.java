package androidx.media3.exoplayer.source.preload;

import androidx.media3.common.MediaItem;

public interface PreloadManagerListener {
    void onCompleted(MediaItem mediaItem);
    void onError(PreloadException exception);
}
