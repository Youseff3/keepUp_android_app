package com.example.android;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Collection of utility functions to formate date and time. Also stores a date that is used as
 * the selected date
 */
public class CalendarUtils {
    public static LocalDate selectedDate;

    /**
     * Formats date in "dd MMMM yyyy" format
     * @param date {@link LocalDate} date to be formatted
     * @return {@link String} formatted date
     */
    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    /**
     * Formats time in "hh:mm:ss a" format
     * @param time {@link LocalTime} time to be formatted
     * @return {@link String} formatted time
     */
    public static String formattedTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return time.format(formatter);
    }

    /**
     * Formats time in "HH:mm" format
     * @param time {@link LocalTime} time to be formatted
     * @return {@link String} formatted time
     */
    public static String formattedShortTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    /**
     * Formats date in "MMMM d" format
     * @param date {@link LocalDate} date to be formatted
     * @return {@link String} formatted date
     */
    public static String monthDayFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        return date.format(formatter);
    }
}

