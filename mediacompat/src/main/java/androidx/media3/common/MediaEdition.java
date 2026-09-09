package androidx.media3.common;

public class MediaEdition {
    public String label;
    public long durationUs;
    public boolean selected;
    public MediaEdition(String label) { this(label, C.TIME_UNSET); }
    public MediaEdition(String label, long durationUs) {
        this.label = label;
        this.durationUs = durationUs;
    }
}
