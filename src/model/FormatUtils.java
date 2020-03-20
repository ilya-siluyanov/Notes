package com.example.isilu.notes.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy",Locale.ENGLISH);
    private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

    public static String getFormattedDate(Date date) {
        return dateFormat.format(date);
    }

    public static Date getParsedDate(String date){
        dateFormat.setLenient(false);
        Date result = new Date();
        try {
            result = dateFormat.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getFormattedTime(Date date){
        return timeFormat.format(date);
    }
}
