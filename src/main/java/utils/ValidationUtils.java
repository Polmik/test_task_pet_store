package utils;

public class ValidationUtils {

    public static boolean equals(Object act, Object exp) {
        if (act == null) {
            return act == exp;
        }
        return act.equals(exp);
    }
}
