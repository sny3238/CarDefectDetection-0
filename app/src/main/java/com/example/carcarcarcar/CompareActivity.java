package com.example.carcarcarcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Collections;
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

    ArrayList<NewDefect> newDefectsList = new ArrayList<NewDefect>();
    ArrayList<Defect> defectList = new ArrayList<Defect>();


    //부분별 결함 종류별로 카운트


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
                            NewDefect newdefect = new NewDefect();

                            JSONObject newDefect = (JSONObject) newDefects.getJSONObject(i);
                            part = newDefect.getString("part");
                            JSONArray defects = newDefects.getJSONArray(i);

                            //new_defects newDefects = new new_defects(newDefectsJSONArray);
                            //newDefects.setPart(newDefectsJSONObject.getString("part"));

                            //defects = (newDefectsJSONObject.getJSONArray("defects"));

                            for (int j = 0;j<defects.length();j++){

                                JSONObject defectsJSONObject = defects.getJSONObject(j);

                                Defect defect = new Defect();

                                defect.setBtmy(defectsJSONObject.getString("btmy"));
                                defect.setBtmx(defectsJSONObject.getString("btmx"));
                                defect.setLabel(defectsJSONObject.getString("label"));
                                defect.setTopx(defectsJSONObject.getString("topx"));
                                defect.setTopy(defectsJSONObject.getString("topy"));

                                defectList.add(defect);
                            }
                            newdefect.setDefectArrayList(defectList);
                            newdefect.setPart(part);


                            newDefectsList.add(newdefect);

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

        Collections.sort(newDefectsList);
    }


    //상세 내역 확인 버튼 클릭하면 part별로 팝업 호출

    public void onffButtonClicked (View v) {

        Intent ffIntent = new Intent(this, ComparePopup.class);
        NewDefect nd1 = newDefectsList.get(0);

    }



}
