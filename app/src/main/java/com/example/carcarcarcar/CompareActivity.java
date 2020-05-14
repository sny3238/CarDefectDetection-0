package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompareActivity extends AppCompatActivity {

    private RequestQueue queue;

    private String rentid;
    private Boolean result;

    private String label,part;
    private Integer topx, topy, btmx, btmy;
    private JSONArray newDefects,defects;

    int index;

    ArrayList<new_defects> newDefectsList = new ArrayList<new_defects>();
    ArrayList<Defects> defectsList = new ArrayList<Defects>();


    //부분별 결함 종류별로 카운트
    private Integer ft_dent, ft_glass, ft_scratch;
    private Integer ff_dent, ff_glass, ff_scratch;
    private Integer rf_dent, rf_glass, rf_scratch;
    private Integer rb_dent, rb_glass, rb_scratch;
    private Integer bt_dent, bt_glass, bt_scratch;
    private Integer bf_dent, bf_glass, bf_scratch;
    private Integer lb_dent, lb_glass, lb_scratch;
    private Integer lf_dent, lf_glass, lf_scratch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        getSupportActionBar().hide();

        Intent getintent = getIntent();

        //rentid= getintent.getDataString("rent_id");

        String url = "http://localhost:3000/showResults/compare";

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    result = response.getBoolean("result");
                    if (result) {


                        newDefects = response.getJSONArray("new_defects");

                        for (int i = 0 ; i<newDefects.length();i++){

                            JSONObject newDefectsJSONObject = newDefects.getJSONObject(i);
                            new_defects newDefects = new new_defects();
                            newDefects.setPart(newDefectsJSONObject.getString("part"));

                            defects = (newDefectsJSONObject.getJSONArray("defects"));

                            for (int j = 0;j<defects.length();j++){

                                JSONObject defectsJSONObject = defects.getJSONObject(i);

                                Defects defects = new Defects();

                                defects.setBtmy(defectsJSONObject.getString("btmy"));
                                defects.setBtmx(defectsJSONObject.getString("btmx"));
                                defects.setLabel(defectsJSONObject.getString("label"));
                                defects.setTopx(defectsJSONObject.getString("topx"));
                                defects.setTopy(defectsJSONObject.getString("topy"));

                                defectsList.add(defects);
                            }

                            newDefectsList.add(newDefects);

                        }

                    } else {
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rent_id", rentid);
                return params;
            }
        };

        queue.add(jsonRequest);

    }


    //상세 내역 확인 버튼 클릭하면 part별로 팝업 호출

    public void onffButtonClicked (View v) {
        Intent ffIntent = new Intent(this, ComparePopup.class);

    }



}
