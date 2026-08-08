package com.fongmi.android.tv.player.mpv;

import androidx.annotation.NonNull;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.Tracks;

import com.fongmi.android.tv.bean.Sub;
import com.fongmi.android.tv.player.effect.PlayerEffect;
import com.fongmi.android.tv.player.engine.PlayerEngine;
import com.fongmi.android.tv.player.media.PlaySpec;

public class MpvPlayerEngine implements PlayerEngine, Player.Listener {

    private final MpvErrorMessageProvider provider;
    private final MpvPlayerEffect effect;
    private final Player player;

    public MpvPlayerEngine(int decode, Player.Listener listener) {
        this.player = null;
        this.provider = new MpvErrorMessageProvider();
        this.effect = new MpvPlayerEffect(null);
    }

    public static boolean isAvailable() {
        return false;
    }

    @Override
    public Type getType() {
        return Type.MPV;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public int getAudioChannelCount() {
        return 0;
    }

    @Override
    public PlayerEffect getEffect() {
        return effect;
    }

    @Override
    public void release() {
    }

    @Override
    public void setSubtitleStyle() {
    }

    @Override
    public boolean addSubtitle(Sub sub) {
        return false;
    }

    @Override
    public void setDecode(int decode) {
    }

    @Override
    public void onTracksChanged(@NonNull Tracks tracks) {
    }

    @Override
    public void start(PlaySpec spec, long startPositionMs) {
    }

    @Override
    public void stop() {
    }

    @Override
    public String getErrorMessage(PlaybackException e) {
        return provider.get(e);
    }

    @Override
    public ErrorAction handleError(PlaybackException e) {
        return ErrorAction.FATAL;
    }
}
