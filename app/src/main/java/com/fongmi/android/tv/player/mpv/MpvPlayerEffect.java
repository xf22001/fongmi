package com.fongmi.android.tv.player.mpv;

import androidx.media3.common.Player;
import com.fongmi.android.tv.player.effect.PlayerEffect;
import com.fongmi.android.tv.player.effect.audio.AudioEffectBands;

public final class MpvPlayerEffect implements PlayerEffect {

    public MpvPlayerEffect(Player player) {
    }

    @Override
    public boolean supportsVideoEffect() {
        return false;
    }

    @Override
    public int getVideoEffectError() {
        return 0;
    }

    @Override
    public boolean supportsVideoSharpness() {
        return false;
    }

    @Override
    public void applyVideoEffect() {
    }

    @Override
    public void previewVideoEffect(boolean original) {
    }

    @Override
    public boolean supportsAudioEffect() {
        return false;
    }

    @Override
    public AudioEffectBands getAudioEffectBands() {
        return AudioEffectBands.STANDARD;
    }

    @Override
    public int getAudioEffectError() {
        return 0;
    }

    @Override
    public void applyAudioEffect() {
    }

    public void clearAudioEffect() {
    }

    @Override
    public void previewAudioEffect(boolean original) {
    }
}
