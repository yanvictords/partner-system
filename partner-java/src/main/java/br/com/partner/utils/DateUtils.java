package br.com.partner.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

public final class DateUtils {

    private static final String CANNOT_PARSE_DATE_TO_PATTERN = "Cannot parse %s to pattern %s";

    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";

    private DateUtils() {
    }

    public static String parse(final String pattern, final LocalDateTime source) {
        try {
            return DateTimeFormatter.ofPattern(pattern).format(source);
        } catch (Exception e) {
            final var message = format(CANNOT_PARSE_DATE_TO_PATTERN, source, pattern);
            System.out.println(message);
            return null;
        }
    }

}
