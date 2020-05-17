package com.example.carcarcarcar;

import org.json.JSONObject;

public class predictions {


    private  String part;

    public JSONObject getDefectsObject() {
        return defectsObject;
    }

    public void setDefectsObject(JSONObject defects) {
        this.defectsObject = defects;
    }

    private JSONObject defectsObject;

    public void setPart(String part) {
        this.part = part;
    }

    public String getPart() {
        return part;
    }


}
