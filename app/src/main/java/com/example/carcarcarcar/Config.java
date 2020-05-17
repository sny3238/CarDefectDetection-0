package com.example.carcarcarcar;

public class Config {
    public String ip="172.30.1.53";
    public String port="3000";
    public String user_id;
    public String car_id;
    public String rent_id;

    public String getUrl(String path){
        return "http://"+this.ip+":"+this.port+path;
    }

    public void setUserId(String user_id){
        this.user_id=user_id;
    }

    public void setCarId(String car_id){
        this.car_id=car_id;
    }

    public void setRentId(String rent_id){
        this.rent_id=rent_id;
    }

    public String getUserId(){
        return this.user_id;
    }

    public String getCarId(){
        return this.car_id;
    }

    public String getRentId(){
        return this.rent_id;
    }

}
