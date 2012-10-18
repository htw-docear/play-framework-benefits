package sync;

public class FileContent {
    private static String content = "default file content";

    public static void set(String newContent) {
        content = newContent;
    }

    public static String get() {
        return content;
    }
}
