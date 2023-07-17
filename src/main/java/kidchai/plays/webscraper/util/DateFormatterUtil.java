package kidchai.plays.webscraper.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class DateFormatterUtil {

    private static final DateTimeFormatter shortFormatter = new DateTimeFormatterBuilder()
            .appendPattern("MMM dd")
            .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
            .toFormatter(Locale.US);

    private static final DateTimeFormatter longFormatter = new DateTimeFormatterBuilder()
            .appendPattern("MMM dd, yyyy")
            .toFormatter(Locale.US);

    private static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendPattern("h:mma EEE, MMM dd, yyyy")
            .toFormatter(Locale.US);

    public static LocalDateTime parseToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    public static LocalDate parseToDate(String date) {
        try {
            return LocalDate.parse(date, longFormatter);
        } catch (DateTimeParseException e) {
            return LocalDate.parse(date, shortFormatter);
        }
    }
}
