package com.npc.news.utils;

/**
 * Created by nguyen tran on 2/9/2018.
 */
import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtils {

    public static final String DATE_FORMAT_ISO_8601 = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_TIME_SERVER_FORMAT =   "yyyy-MM-dd'T'HH:mm:ss";
    public static String DATE_TIME_SERVER_FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * Generic date to string converter
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(date);
    }

    /**
     * Generic string to date converter
     *
     * @param value
     * @param format
     * @return
     */
    public static Date dateFromString(String value, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date dateFromSecond(double second) {
        return new Date((long) second);
    }

    public static Date dateFromStringUTC(String value, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return simpleDateFormat.parse(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get a date object from an ISO8601 date
     *
     * @param iso8601Date
     */
    public static Date parseISO8601(String iso8601Date) {
        Date parsedDate = dateFromString(iso8601Date, DATE_FORMAT_ISO_8601);
        return parsedDate == null ? new Date() : parsedDate;
    }

    public static String getDateFormatAdd(Date d1, Date d2) {
        long num = d1.getTime() + d2.getTime();
        Date sum = new Date(num);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm a");
        return sdf.format(sum);
    }

    /**
     * Date diff components
     */
    public static class DateDiff {
        private final Date date1;
        private final Date date2;

        private final Long millis;
        private int seconds = 0;
        private int minutes = 0;
        private int hours = 0;
        private int days = 0;

        public DateDiff(Date date1, Date date2) {
            this.date1 = date1;
            this.date2 = date2;

            this.millis = this.date1.getTime() - this.date2.getTime();
            if (this.millis > 1000) {
                this.seconds = Integer.valueOf(String.valueOf(millis / 1000));
                if (this.seconds > 60) this.minutes = seconds / 60;
                if (this.minutes > 60) this.hours = minutes / 60;
                if (this.hours > 24) this.days = hours / 24;
            }
        }

        public Long getTime() {
            return millis;
        }

        public int getSeconds() {
            return seconds;
        }

        public int getMinutes() {
            return minutes;
        }

        public int getHours() {
            return hours;
        }

        public int getDays() {
            return days;
        }
    }

    /**
     * Generate list hours string [0-24]
     *
     * @return
     */
    public static String[] generateHoursWithZero() {
        int i = 0;
        String[] hours = new String[25];
        while (i < 25) {
            hours[i] = String.valueOf(i);
            i++;
        }
        return hours;
    }

    public static String dayOfTheWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        return sdf.format(date);
    }

    public static String monthOfTheYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        return sdf.format(date);
    }

    public static String monthDayYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd,yyyy");
        return sdf.format(date);
    }

    public static String hourAmPm(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        return sdf.format(date);
    }

    /**
     * Get the display time
     *
     * @param context        The application content
     * @param dateString     The the past date time.
     * @param dateTimeFormat Date time format
     * @return
     */
    public static String getDisplayTime(Context context, String dateString, String dateTimeFormat) {
        Date today = new Date();
        Date serverTime = DateUtils.dateFromStringUTC(dateString, dateTimeFormat);
        if (serverTime == null)
            return "";

        DateDiff diff = new DateDiff(today, serverTime);
        long seconds = diff.getSeconds();
        long minutes = diff.getMinutes();
        long days = diff.getDays();

        if (seconds < 60) {
            return "just now";
        } else if (minutes <= 30) {
            return String.format("%s minute%s ago", String.valueOf(minutes), minutes >= 2 ? "s" : "");
        } else if (days < 1) {
            return dateToString(serverTime, "h:mm a");
        } else if (days <= 7) {
            return dateToString(serverTime, "EEEE");
        } else if (days <= 365) {
            return dateToString(serverTime, "MMM dd");
        } else {
            return dateToString(serverTime, "MMM dd, yyyy");
        }
    }

    /**
     * Get the display time
     *
     * @param context
     * @param dateString
     * @return
     */
    public static String getDisplayTime(Context context, String dateString) {
        String result = getDisplayTime(context, dateString, DATE_TIME_SERVER_FORMAT);
        if (TextUtils.isEmpty(result)) {
            result = getDisplayTime(context, dateString, DATE_TIME_SERVER_FORMAT_2);
        }
        return result;
    }

    /**
     * Get the display time
     *
     * @param context      The application content
     * @param milliseconds The the past date time.
     * @return
     */
    public static String getDisplayTime(Context context, long milliseconds) {

        Date today = new Date();
        Date serverTime = new Date(milliseconds);
        if (serverTime == null)
            return "";

        DateDiff diff = new DateDiff(today, serverTime);
        long seconds = diff.getSeconds();
        long minutes = diff.getMinutes();
        long days = diff.getDays();

        if (seconds < 60) {
            return "just now";
        } else if (minutes <= 30) {
            return String.format("%s minute%s ago", String.valueOf(minutes), minutes >= 2 ? "s" : "");
        } else if (days < 1) {
            return dateToString(serverTime, "h:mm a");
        } else if (days <= 7) {
            return dateToString(serverTime, "EEEE");
        } else if (days <= 365) {
            return dateToString(serverTime, "MMM dd");
        } else {
            return dateToString(serverTime, "MMM dd, yyyy");
        }
    }

    /**
     * Get the display time
     *
     * @param context      The application content
     * @param milliseconds The the past date time.
     * @return
     */
    public static String getChatDisplayTime(Context context, long milliseconds) {

        Date today = new Date();
        Date serverTime = new Date(milliseconds);

        DateDiff diff = new DateDiff(today, serverTime);
        long seconds = diff.getSeconds();
        long minutes = diff.getMinutes();
        long days = diff.getDays();

        if (seconds < 60) {
            return "just now";
        } else if (minutes <= 30) {
            return String.format("%s minute%s ago", String.valueOf(minutes), minutes >= 2 ? "s" : "");
        } else if (days < 1) {
            return dateToString(serverTime, "h:mm a");
        } else if (days <= 7) {
            return dateToString(serverTime, "E, MMM dd, h:mm a");
        } else if (days <= 365) {
            return dateToString(serverTime, "MMM dd, h:mm a");
        } else {
            return dateToString(serverTime, "MMM dd, yyyy");
        }
    }

    public static int calculateAge(String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_SERVER_FORMAT_2);
        try {
            Date date = simpleDateFormat.parse(dateString);
            Date now = new Date();
            long period = now.getTime() - date.getTime();
            return (int)(((period/1000)/60)/60/24)/365;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
