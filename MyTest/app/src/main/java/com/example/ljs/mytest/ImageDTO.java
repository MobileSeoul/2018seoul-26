package com.example.ljs.mytest;

import java.util.HashMap;
import java.util.Map;

public class ImageDTO {
    public String uid;
    public String userId;

    public String imageUrl;
    public String subImage1;
    public String subImage2;
    public String subImage3;
    public String title;
    public String description;
    public Double lat;
    public Double lon;
    public String FoodKinds;
    public String PriceRange;
    public String operatingTime;
    public String phone;

    public String Arabic;
    public String HalalFood;
    public String PrayerRoom;
    public String email;
    public String web;

    public String Explanation;
    public String fee;

    public String Compass;
    public String Mat;
    public String PrayerCostume;
    public String Quran;

    public int likeCount = 0;
    public Map<String, Boolean> likes = new HashMap<>();


}
