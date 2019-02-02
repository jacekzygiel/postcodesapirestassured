package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestData {

    public static final List<String> INVALID_POST_CODES_LIST =
            new ArrayList<String>(Arrays.asList("123 ABC", "O2L5 51A", "XXX 222"));

    public static final List<String> VALID_POST_CODES_LIST =
            new ArrayList<String>(Arrays.asList("B92 7BD", "M32 0JG", "NE30 1DP"));


    public static String getRandomValidPostCode() {
        return VALID_POST_CODES_LIST.get(new Random().nextInt(VALID_POST_CODES_LIST.size()));
    }

    public static String getRandomInvalidPostCode() {
        return INVALID_POST_CODES_LIST.get(new Random().nextInt(INVALID_POST_CODES_LIST.size()));
    }
}
