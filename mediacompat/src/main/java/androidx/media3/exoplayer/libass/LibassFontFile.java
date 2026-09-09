package androidx.media3.exoplayer.libass;

import java.io.File;
import java.io.IOException;

public class LibassFontFile {

    public static String getFamilyName(File file) throws IOException {
        if (file == null || !file.exists()) return null;
        String name = file.getName();
        int dot = name.lastIndexOf('.');
        return dot > 0 ? name.substring(0, dot) : name;
    }
}
