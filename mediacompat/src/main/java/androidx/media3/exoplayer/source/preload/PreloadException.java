package androidx.media3.exoplayer.source.preload;

import androidx.media3.common.MediaItem;

public class PreloadException extends Exception {
    public final MediaItem mediaItem;
    public PreloadException(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }
}
