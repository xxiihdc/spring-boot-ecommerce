package com.poly.ductr.app.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
     static SimpleDateFormat formater = new SimpleDateFormat();
     public static Date toDate(String date,String pattern){
         try {
             Date d =  new SimpleDateFormat(pattern).parse(date);
             return d;
         } catch (ParseException ex) {
             Date d = null;
             try {
                 d = new SimpleDateFormat("MM/dd/yyyy").parse(date);
                 return d;
             } catch (ParseException e) {
                 return new Date();
             }

         }  
     }
     public static String toString(Date date, String pattern){
         formater.applyPattern(pattern);
         return formater.format(date);
     }
     public static Date addDays(Date date, long days){
         date.setTime(date.getTime()+days*24*60*60*1000);
         return date;
     }
}
