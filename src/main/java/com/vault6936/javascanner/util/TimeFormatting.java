package com.vault6936.javascanner.util;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class TimeFormatting {
    private TimeFormatting(){}
    public static String formatToTime(int value) {
        if(value < 10) {
            return "0" + (value);
        }
        return String.valueOf(value);
    }
    public static String toTwelveHour(int hour, int minute, int second) {
        String formattedMinute = formatToTime(minute);
        String formattedSecond = formatToTime(second);
        String timeOfDay;
        if (hour > 11) {
            if (hour > 12) hour -= 12;
            timeOfDay = " PM";
        } else {
            timeOfDay = " AM";
        }
        return hour + ":" + formattedMinute + ":" + formattedSecond + timeOfDay;
    }
    public static String timeToString(ZonedDateTime time) {
        String string = toTwelveHour(time.getHour(), time.getMinute(), time.getSecond());
        return string;
    }
    private static String ifZero(long value) {
        if (value == 0) {
            return "";
        }
        return String.valueOf(value);
    }
    public static String timeUntil(ZonedDateTime time1, ZonedDateTime time2) {
        long totalSeconds;
        long remainder;
        totalSeconds = time1.until(time2, ChronoUnit.SECONDS);
        long hours = (long) Math.floor(totalSeconds / (60*60));
        remainder = totalSeconds % (60*60);
        long minutes = (long) Math.floor(remainder / 60);
        long seconds = remainder % 60;
        return ifZero(hours) + "h " + ifZero(minutes) + "m " + seconds + "s";
    }
    public static long getCurrentTime() {
        return ZonedDateTime.now().toEpochSecond() * 1000;
    }
}
