package com.fongmi.android.tv.player.engine;

import androidx.media3.common.C;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.MediaChapter;
import androidx.media3.common.MediaEdition;
import java.util.List;
import java.util.Collections;

import com.fongmi.android.tv.bean.Sub;
import com.fongmi.android.tv.player.effect.PlayerEffect;
import com.fongmi.android.tv.player.media.PlaySpec;

public interface PlayerEngine {

    int SOFT = 0;
    int HARD = 1;

    Type getType();

    Player getPlayer();

    int getAudioChannelCount();

    void release();

    void setDecode(int decode);

    default PlayerEffect getEffect() {
        return PlayerEffect.NONE;
    }

    void start(PlaySpec spec, long startPositionMs);

    default void preload(PlaySpec spec, long startPositionMs) {
    }

    default void clearPreload() {
    }

    void stop();

    default void setSubtitleStyle() {
    }

    default boolean addSubtitle(Sub sub) {
        return false;
    }

    default List<MediaChapter> getCurrentMediaChapters() {
        return Collections.emptyList();
    }

    default List<MediaEdition> getCurrentMediaEditions() {
        return Collections.emptyList();
    }

    default void selectChapter(MediaChapter chapter) {
    }

    default void selectEdition(MediaEdition edition) {
    }

    default long getTextOffsetMs() {
        return 0;
    }

    default void setTextOffsetMs(long offsetMs) {
    }

    default long getAudioOffsetMs() {
        return 0;
    }

    default void setAudioOffsetMs(long offsetMs) {
    }

    String getErrorMessage(PlaybackException e);

    ErrorAction handleError(PlaybackException e);

    enum ErrorAction {
        RECOVERED,
        DECODE,
        FATAL
    }

    enum Type {
        EXO,
        MPV
    }
}
