package utils;

public class StringUtils {

    private StringUtils() {
    }

    public static String concat(String... text) {
        if (text.length == 0) {
            throw new IllegalArgumentException("Text array should not be empty");
        } else {
            StringBuilder sb = new StringBuilder();
            String[] var2 = text;
            int var3 = text.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String part = var2[var4];
                sb.append(part);
            }

            return sb.toString();
        }
    }
}
