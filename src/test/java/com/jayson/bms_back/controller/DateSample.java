package com.jayson.bms_back.controller;


import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateSample {

    public static void main(String[] args) throws ParseException {
        long l = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date(l);
        String format = sdf.format(date);
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, 30);// num为增加的天数，可以改变的
        String format1 = sdf.format(ca.getTime());
        System.out.println(format);
        System.out.println(format1);
        long time = ca.getTime().getTime();
        Date date1 = new Date(time);
        System.out.println(sdf.format(date1));

    }
}
