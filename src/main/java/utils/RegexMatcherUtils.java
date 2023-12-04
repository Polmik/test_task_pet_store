package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcherUtils {

    private RegexMatcherUtils() {
    }

    public static boolean isTextInteger(String text) {
        return regexIsMatch(text, "^[-+]?\\d+$");
    }

    public static String regexGetMatch(String text, String regex) {
        return regexGetMatchGroup(text, regex, 0, false);
    }

    public static String regexGetMatchGroup(String text, String regex, int groupIndex) {
        return regexGetMatchGroup(text, regex, groupIndex, false);
    }

    public static String regexGetMatchGroup(String text, String regex, int groupIndex, boolean matchCase) {
        Pattern p = regexGetPattern(regex, matchCase);
        Matcher m = p.matcher(text);
        return m.find() ? m.group(groupIndex) : null;
    }

    private static Pattern regexGetPattern(String regex, boolean matchCase) {
        int flags = matchCase ? 0 : 66;
        return Pattern.compile(regex, flags);
    }

    public static boolean regexIsMatch(String text, String regex) {
        return regexIsMatch(text, regex, false);
    }

    private static boolean regexIsMatch(String text, String regex, boolean matchCase) {
        Pattern p = regexGetPattern(regex, matchCase);
        Matcher m = p.matcher(text);
        return m.find();
    }
}

