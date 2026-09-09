package androidx.media3.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.common.Player;

public class PlayerSeekView extends FrameLayout {

    private final DefaultTimeBar timeBar;
    private Player player;
    private final Runnable updateProgressAction = this::updateProgress;

    public PlayerSeekView(@NonNull Context context) {
        this(context, null);
    }

    public PlayerSeekView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerSeekView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        timeBar = new DefaultTimeBar(context, attrs);
        timeBar.setId(R.id.exo_progress);
        addView(timeBar, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public TimeBar getTimeBar() {
        return timeBar;
    }

    public void setPlayer(@Nullable Player player) {
        if (this.player == player) {
            return;
        }
        if (this.player != null) {
            this.player.removeListener(playerListener);
        }
        this.player = player;
        if (player != null) {
            player.addListener(playerListener);
            updateProgress();
        } else {
            removeCallbacks(updateProgressAction);
        }
    }

    private void updateProgress() {
        removeCallbacks(updateProgressAction);
        if (player == null) {
            return;
        }
        long position = player.getCurrentPosition();
        long duration = player.getDuration();
        long bufferedPosition = player.getBufferedPosition();
        timeBar.setPosition(position);
        timeBar.setDuration(duration);
        timeBar.setBufferedPosition(bufferedPosition);
        
        int playbackState = player.getPlaybackState();
        if (player.getPlayWhenReady() && playbackState != Player.STATE_IDLE && playbackState != Player.STATE_ENDED) {
            long delayMs = 1000 - (position % 1000);
            postDelayed(updateProgressAction, delayMs);
        }
    }

    private final Player.Listener playerListener = new Player.Listener() {
        @Override
        public void onPlaybackStateChanged(int playbackState) {
            updateProgress();
        }

        @Override
        public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
            updateProgress();
        }

        @Override
        public void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int reason) {
            updateProgress();
        }

        @Override
        public void onEvents(@NonNull Player player, @NonNull Player.Events events) {
            updateProgress();
        }
    };
}
