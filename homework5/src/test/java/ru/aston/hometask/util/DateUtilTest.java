package ru.aston.hometask.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {
    @ParameterizedTest
    @MethodSource("getValidDateStrings")
    void when_dateStringIsValid_then_returnTrue(String date) {
        assertTrue(DateUtil.isValidDate(date));
    }

    @ParameterizedTest
    @MethodSource("getInvalidDateStrings")
    void when_dateStringIsInvalid_then_returnFalse(String date) {
        assertFalse(DateUtil.isValidDate(date));
    }

    static Stream<String> getValidDateStrings() {
        return Stream.of(
                "2020-01-01",
                "1991-12-31",
                "1900-02-28");
    }

    static Stream<String> getInvalidDateStrings() {
        return Stream.of(
                "1-1-2020",
                "12-31-2018",
                "01-12-24");
    }

    @Test
    void  when_parseValidDateString_then_returnLocalDate() {
        LocalDate expectResult = LocalDate.of(2000, 1, 1);

        LocalDate actualResult = DateUtil.parseDateFromString("2000-01-01");

        assertEquals(expectResult, actualResult);
    }
}