package utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.AbstractMap.*;

public final class TestData {

    public static final List<String> INVALID_POST_CODES_LIST =
            new ArrayList<>(Arrays.asList("123 ABC", "O2L5 51A", "XXX 222"));

    public static final List<String> VALID_POST_CODES_LIST =
            new ArrayList<>(Arrays.asList("B92 7BD", "M32 0JG", "NE30 1DP"));

    public static final Map<String, Object> validGpsData = Stream.of(
            new SimpleEntry<>("lon", "0.629834723775309"),
            new SimpleEntry<>("lat", "51.7923246977375"),
            new SimpleEntry<>("postCodes", new ArrayList<>(Arrays.asList("CM8 1EF", "CM8 1EU", "CM8 1PH", "CM8 1PQ"))))
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

    public static String getRandomValidPostCode() {
        return VALID_POST_CODES_LIST.get(new Random().nextInt(VALID_POST_CODES_LIST.size()));
    }

    public static String getRandomInvalidPostCode() {
        return INVALID_POST_CODES_LIST.get(new Random().nextInt(INVALID_POST_CODES_LIST.size()));
    }
}
