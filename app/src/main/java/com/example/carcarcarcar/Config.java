package com.example.carcarcarcar;

public class Config {

    public static String getUrl(String path){
        String ip="172.30.1.6";
        String port="3000";
        return "http://"+ip+":"+port+path;
    }

}
