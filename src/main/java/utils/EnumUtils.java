package utils;

import java.util.EnumSet;
import java.util.Random;

public class EnumUtils {
    private static final Random PRNG = new Random();

    public static <T extends Enum> T randomValue(Class<T> clazz)   {
        var values = EnumSet.allOf(clazz).toArray();
        return (T) values[PRNG.nextInt(values.length)];
    }

}
