package com.psm.lifeorganizer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by pmaestri on 10/19/2016.
 */
public class DateUtil {

    private static SimpleDateFormat birthdayFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static String getFormattedDateAndTime(Date date) {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(date);
    }

    public static String formatBirthday(Date birthday) {
        return birthdayFormat.format(birthday);
    }

    public static Date parseBirthday(String birthday) {
        try {
            return birthdayFormat.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getDiffYears(Date from, Date to) {
        Calendar first = new GregorianCalendar();
        first.setTime(from);
        Calendar last = new GregorianCalendar();
        last.setTime(to);
        int diff = last.get(Calendar.YEAR) - first.get(Calendar.YEAR);
        if (first.get(Calendar.MONTH) > last.get(Calendar.MONTH) ||
                (first.get(Calendar.MONTH) == last.get(Calendar.MONTH) && first.get(Calendar.DATE) > last.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

}
