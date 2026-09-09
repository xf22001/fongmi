package androidx.media3.exoplayer.libass;

import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.Renderer;

public class LibassPlaybackSession {

    public LibassPlaybackSession(LibassConfiguration configuration, boolean enabled) {
    }

    public boolean isAvailable() {
        return false;
    }

    public static class MediaComponents {
        public androidx.media3.extractor.ExtractorsFactory extractorsFactory;
        public androidx.media3.extractor.text.SubtitleParser.Factory subtitleParserFactory;

        public MediaComponents(Object... args) {
        }
    }

    public MediaComponents createMediaComponents(MediaItem item, Object extractorsFactory) {
        return null;
    }

    public void setPreloadMediaItem(@Nullable MediaItem mediaItem) {
    }

    public void close() {
    }

    public Renderer createClockRenderer() {
        return null;
    }

    public void setBottomPositionFraction(float fraction) {
    }

    public void setSecondaryBottomPositionFraction(float fraction) {
    }

    public void setFontScale(float scale, boolean apply) {
    }
}
