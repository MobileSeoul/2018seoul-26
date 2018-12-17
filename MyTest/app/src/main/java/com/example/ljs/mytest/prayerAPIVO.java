package com.example.ljs.mytest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class prayerAPIVO {
    private static List<HashMap<String,String>> prayinList = null;
    private final static String location = "Seoul, SOUTHKOREA";
    private static ArrayList<String> prayTime=null;
    private static int checkflag=0;

    private String getCurrentTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("dd-MM-yyyy");
        final String str = dayTime.format(new Date(time));
        return str;
    }
    public static int getCheckflag() {
        return checkflag;
    }

    public String getLocation()
    {
        return location;
    }
    public String getCurrent_date()
    {
        return getCurrentTime();
    }

    public void setprayerTime(List<HashMap<String,String>> str)
    {
        prayinList=str;
        checkflag=1;
    }

    public List<HashMap<String, String>> getPrayinList() {
        return prayinList;
    }
}
