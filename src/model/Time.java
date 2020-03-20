package com.example.isilu.notes.model;

import java.util.Date;

import static com.example.isilu.notes.model.FormatUtils.*;

public class Time {
    private static final String formatString = "%s:%s";
    private String hour;
    private String minute;

    public Time(int hour,int minute){
        this(Integer.toString(hour),Integer.toString(minute));
    }

    Time(String hour, String minute) {
        if (hour.equals("0")||(Integer.parseInt(hour)<10&&!hour.startsWith("0")))
            this.hour ="0"+hour;
        else
            this.hour = hour;

        if (minute.equals("0")||(Integer.parseInt(minute)<10&&!minute.startsWith("0")))
            this.minute ="0"+minute;
        else
            this.minute= minute;
    }

    public Time(String sample) {
        this(sample.split(":")[0], sample.split(":")[1]);
    }

    public Time(Date date) {
        this(getFormattedTime(date));
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return String.format(formatString, hour, minute);
    }
}
