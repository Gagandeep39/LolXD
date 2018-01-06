package com.example.test.nuvoco3.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarHelper {
    public static String convertSmallDateToJson(String start_dt) {
//        String pattern = "E, dd MMM yyyy HH:mm:ss 'GMT'";
        String pattern = "E, dd MMM yyyy";
        DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = formatter.parse(start_dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat newFormat = new SimpleDateFormat(pattern);
        String finalString = newFormat.format(date);
//        Log.i("lol", "convertSmallDateToJson: " + finalString);
        return finalString;


    }

    public static String convertJsonDateToSmall(String start_dt) {
        String pattern = "E, dd MMM yyyy HH:mm:ss 'GMT'";
        DateFormat formatter = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = formatter.parse(start_dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-mm-dd");
        String finalString = newFormat.format(date);
//        Log.i("lol", "convertSmallDateToJson: " + finalString);
        return finalString;


    }

    public static String convertDateTimeToDate(String start_dt) {
//        String pattern = "E, dd MMM yyyy HH:mm:ss 'GMT'";
        String pattern = "yyyy-mm-dd";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(start_dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat newFormat = new SimpleDateFormat(pattern);
        String finalString = newFormat.format(date);
//        Log.i("lol", "convertSmallDateToJson: " + finalString);
        return finalString;


    }

    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


}
