package utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public final class Utilities {

    public static StringBuilder parseInputStream(InputStream stream) throws IOException {
        int c;
        StringBuilder stringBuilder = new StringBuilder();
        while ((c = stream.read()) != -1) {
            stringBuilder.append((char) c);
        }

        return stringBuilder;
    }

    public static String getProjectPath() {
        return FileSystems
                .getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString();
    }
}
