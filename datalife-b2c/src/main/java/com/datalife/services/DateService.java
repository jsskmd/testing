package com.datalife.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class is for Date Conversions
 *
 * Created by supriya gondi on 11/1/2014.
 */
public class DateService {

    /*private  Date cvtToGmt( Date date ){
        TimeZone tz = TimeZone.getDefault();
        Date ret = new Date( date.getTime() - tz.getRawOffset() );

        // if we are now in DST, back off by the delta.  Note that we are checking the GMT date, this is the KEY.
        if ( tz.inDaylightTime( ret )){
            Date dstDate = new Date( ret.getTime() - tz.getDSTSavings() );

            // check to make sure we have not crossed back into standard time
            // this happens when we are on the cusp of DST (7pm the day before the change for PDT)
            if ( tz.inDaylightTime( dstDate )){
                ret = dstDate;
            }
        }
        return ret;
    }*/

    /**
     * This method is to get Today's Date in specified format
     *
     * @return String representation of Date
     */
    public static String getTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getTodayDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getTodayDateTimeSec() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Date stringToTime(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        Date date =null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

      public static Date stringToDateDMY(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Date date =null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * This method is to convert string to Date Object in specifies format
     *
      * @param dateString
     * @return Date
     */
    public static Date stringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date =null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



    /**
     * This method is to convert Date Object to String in specified format
     *
     * @param date
     * @return string representation of Date
     */
    public static String dateToString(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateFormat.format(date);
    }
    public static String dateToStringDMY(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
        return dateFormat.format(date);
    }

    public static String dateToStringConversion(Date date)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static Date stringToDateConversion(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date =null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getCurrentDateInDMYFormat()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return stringToDateConversion(dateFormat.format(new Date()));
    }



    public static int daysBetween(long t1, long t2) {
        return (int) ((t2 - t1) / (1000 * 60 * 60 * 24));
    }
}
