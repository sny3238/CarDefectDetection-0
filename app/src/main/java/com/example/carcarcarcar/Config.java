package com.example.carcarcarcar;

public class Config {

    public static String getUrl(String path){
        String ip="10.200.150.78";
        String port="3000";
        return "http://"+ip+":"+port+path;
    }

}
