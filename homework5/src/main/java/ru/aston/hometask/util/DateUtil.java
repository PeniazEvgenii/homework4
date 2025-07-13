package ru.aston.hometask.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class DateUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public LocalDate parseDateFromString(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static boolean isValidDate(String date) {
        try {
            parseDateFromString(date);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    public static int getAge(String date) {
        LocalDate birthDate = parseDateFromString(date);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
