package com.example.carcarcarcar;

public class Config {

    public static String user_id;
    public static String rent_id;
    public static String car_id;
    public static Boolean is_renting;
    public static Boolean photos_state_after;
    public static Boolean photos_state_before;
    public static String url="http://yoco.ap.ngrok.io";

    public static String getUrl(String path){
        return url+path;
    }

}