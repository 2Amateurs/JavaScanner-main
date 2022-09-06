package com.vault6936.javascanner.util;

import java.time.ZonedDateTime;
import java.util.TimeZone;

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
        if (hour > 12) {
            hour -= 12;
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
}
