package androidx.media3.mpvplayer;

import androidx.media3.common.ForwardingPlayer;
import androidx.media3.common.Player;
import androidx.media3.common.MediaItem;
import androidx.media3.mpvplayer.audio.MpvAudioFilter;
import androidx.media3.mpvplayer.video.MpvVideoEqualizer;
import java.util.List;
import androidx.media3.common.MediaChapter;
import androidx.media3.common.MediaEdition;

public class SafeMpvPlayer extends ForwardingPlayer implements MpvPlayer {

    public SafeMpvPlayer(Player player) {
        super(player);
    }

    @Override
    public int getAudioChannelCount() {
        return 2;
    }

    @Override
    public void setAudioOutputListener(AudioOutputListener listener) {
    }

    @Override
    public void setDecode(int decode) {
    }

    @Override
    public boolean addSubtitle(MediaItem.SubtitleConfiguration subtitleConfiguration) {
        return false;
    }

    @Override
    public void setSubtitleOptions(MpvSubtitleOptions options) {
    }

    @Override
    public int getVideoEffectsSupport() {
        return VIDEO_EFFECTS_UNSUPPORTED_DIRECT_DOLBY_VISION_OUTPUT;
    }

    @Override
    public boolean isVideoSharpnessSupported() {
        return false;
    }

    @Override
    public int getAudioEffectsSupport() {
        return AUDIO_EFFECTS_UNSUPPORTED_PASSTHROUGH;
    }

    @Override
    public boolean setAudioFilter(MpvAudioFilter filter) {
        return true;
    }

    @Override
    public void setVideoEqualizer(MpvVideoEqualizer equalizer) {
    }

    // Aligned methods from custom Media3
    public List<MediaChapter> getCurrentMediaChapters() {
        return List.of();
    }

    public List<MediaEdition> getCurrentMediaEditions() {
        return List.of();
    }

    public void selectChapter(MediaChapter chapter) {
    }

    public void selectEdition(MediaEdition edition) {
    }

    public long getTextOffsetMs() {
        return 0;
    }

    public void setTextOffsetMs(long offsetMs) {
    }

    public long getAudioOffsetMs() {
        return 0;
    }

    public void setAudioOffsetMs(long offsetMs) {
    }
}
