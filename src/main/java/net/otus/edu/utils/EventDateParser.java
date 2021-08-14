package net.otus.edu.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EventDateParser {
    private static final Logger LOGGER = LogManager.getLogger(EventDateParser.class);
    private EventDateParser() {
        throw new IllegalStateException("Utility class");
    }

    private static final String TITER_WITH_SPACES = " - ";
    private static final String TWO_CHAR_DAY_DATE_PATTERN = "dd MMM yyyy";
    private static final String ONE_CHAR_DAY_DATE_PATTERN = "d MMM yyyy";

    public static LocalDate getLastDateAtString(String stringDate) {
        String[] split = stringDate.split(TITER_WITH_SPACES);
        return parseStringToDate(split[1]);
    }

    public static LocalDate getFirstDateAtString(String stringDate) {
        String[] split = stringDate.split(TITER_WITH_SPACES);
        String date = split[0] + " " + split[1].split(" ")[2];
        return parseStringToDate(date);
    }

    private static LocalDate parseStringToDate(String stringDate) {
        DateTimeFormatter formatter;
        if (stringDate.charAt(1) != ' ') {
            formatter = DateTimeFormatter.ofPattern(TWO_CHAR_DAY_DATE_PATTERN, Locale.ENGLISH);
        } else {
            formatter = DateTimeFormatter.ofPattern(ONE_CHAR_DAY_DATE_PATTERN, Locale.ENGLISH);
        }
        LocalDate date = LocalDate.parse(stringDate, formatter);
        LOGGER.info("Полученная строка '{}' преобразована в дату: {}", stringDate, date);
        return date;

    }
}
