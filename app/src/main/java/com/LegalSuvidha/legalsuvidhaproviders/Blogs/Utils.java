package com.LegalSuvidha.legalsuvidhaproviders.Blogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Utils {

    public static ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#ffeead")),
                    new ColorDrawable(Color.parseColor("#93cfb3")),
                    new ColorDrawable(Color.parseColor("#fd7a7a")),
                    new ColorDrawable(Color.parseColor("#faca5f")),
                    new ColorDrawable(Color.parseColor("#1ba798")),
                    new ColorDrawable(Color.parseColor("#6aa9ae")),
                    new ColorDrawable(Color.parseColor("#ffbf27")),
                    new ColorDrawable(Color.parseColor("#d93947"))
            };

    public static ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    public static String DateToTimeFormat(String oldStringDate) throws ParseException {
        PrettyTime p = new PrettyTime();
        String isTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.ENGLISH);
        Date date = sdf.parse(oldStringDate);
        isTime = p.format(date);
        return isTime;
    }

    public static String DateFormat(String oldStringDate){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldStringDate);
            newDate = dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldStringDate;
        }
        return newDate;
    }

    public static String DateToDateTimeFormat(String oldStringDate){
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy", new Locale(getCountry()));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(oldStringDate);
            newDate = dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldStringDate;
        }
        return newDate;
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = String.valueOf(locale.getCountry());
        return country.toLowerCase();
    }

    public static String getLanguage(){
        Locale locale = Locale.getDefault();
        String language = String.valueOf(locale.getLanguage());
        return language;
    }
}
