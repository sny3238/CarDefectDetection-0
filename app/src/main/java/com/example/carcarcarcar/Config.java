package com.example.carcarcarcar;

public class Config {

    public static String getUrl(String path){
        String ip="192.168.0.101";
        String port="3000";
        return "http://"+ip+":"+port+path;
    }

}
