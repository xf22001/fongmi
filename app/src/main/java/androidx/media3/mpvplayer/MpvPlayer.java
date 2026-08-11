package androidx.media3.mpvplayer;

import androidx.media3.common.Player;
import androidx.media3.common.MediaItem;
import androidx.media3.mpvplayer.audio.MpvAudioFilter;
import androidx.media3.mpvplayer.video.MpvVideoEqualizer;
import androidx.media3.exoplayer.ExoPlayer;

public interface MpvPlayer extends Player {

    int VIDEO_EFFECTS_SUPPORTED = 1;
    int VIDEO_EFFECTS_UNSUPPORTED_DIRECT_DOLBY_VISION_OUTPUT = 2;
    int AUDIO_EFFECTS_SUPPORTED = 1;
    int AUDIO_EFFECTS_UNSUPPORTED_PASSTHROUGH = 2;

    static boolean isAvailable() {
        return false;
    }

    int getAudioChannelCount();

    void setAudioOutputListener(AudioOutputListener listener);

    void setDecode(int decode);

    void addSubtitle(MediaItem.SubtitleConfiguration subtitleConfiguration);

    void setSubtitleOptions(MpvSubtitleOptions options);

    int getVideoEffectsSupport();

    boolean isVideoSharpnessSupported();

    int getAudioEffectsSupport();

    boolean setAudioFilter(MpvAudioFilter filter);

    void setVideoEqualizer(MpvVideoEqualizer equalizer);

    interface AudioOutputListener {
        void onAudioOutput();
    }

    class Builder {
        public Builder(android.content.Context context) {}
        public Builder setDecode(int decode) { return this; }
        public Builder setConfig(MpvPlayerConfig config) { return this; }
        public MpvPlayer build() {
            ExoPlayer exoPlayer = new ExoPlayer.Builder(com.fongmi.android.tv.App.get()).build();
            return new SafeMpvPlayer(exoPlayer);
        }
    }
}
