package androidx.media3.common;

public class MediaChapter {
    public String label;
    public long timeUs;
    public boolean selected;
    public MediaChapter(String label) { this(label, C.TIME_UNSET); }
    public MediaChapter(String label, long timeUs) {
        this.label = label;
        this.timeUs = timeUs;
    }
}
