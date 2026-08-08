package com.fongmi.android.tv.player.exo;

import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;

import com.fongmi.android.tv.App;

final class ExoPlayerSession {

    private final DefaultTrackSelector trackSelector;
    private final ExoPlayer player;

    ExoPlayerSession(int decode, Player.Listener listener, AudioProcessor audioProcessor) {
        this.trackSelector = ExoUtil.buildTrackSelector(decode);
        this.player = ExoUtil.buildPlayer(listener, trackSelector, ExoUtil.buildRenderersFactory(audioProcessor), ExoUtil.buildLoadControl());
    }

    ExoPlayer player() {
        return player;
    }

    void setDecode(int decode) {
        ExoUtil.setDecodePreferences(trackSelector, decode);
    }

    void preload(MediaItem mediaItem, long startPositionMs) {
    }

    @Nullable
    MediaSource usePreloadedMediaSource(MediaItem mediaItem) {
        return null;
    }

    void clearPreload() {
    }

    void release() {
        player.release();
    }
}
