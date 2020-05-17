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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AfterPastHistory extends AppCompatActivity {

    private RequestQueue queue;

    private String rentid,beforeafter;

    private String state = "a";

    private Boolean result;

    private String label,part;
    private Integer topx, topy, btmx, btmy;
    private JSONArray predictionsJSON,defects;

    ArrayList<predictions> predictionsList = new ArrayList<predictions>();
    ArrayList<Defect> defectList = new ArrayList<Defect>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_past_history);
        getSupportActionBar().hide();

        Intent getintent = getIntent();
        rentid=getintent.getStringExtra("rent_id");
        beforeafter = getintent.getStringExtra("before_after"); //beforeafter 받아오기
    }

    public void onCompareButtonClicked(View v){
        queue = Volley.newRequestQueue(this);

        String url = "http://localhost:3000/showResults/yolo";

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    result = response.getBoolean("result");
                    if (result) {

                        predictionsJSON = response.getJSONArray("predictions");


                        for (int i = 0 ; i<predictionsJSON.length();i++){

                            JSONObject predictionsObject = predictionsJSON.getJSONObject(i);
                            predictions Predictions = new predictions();
                            Predictions.setPart(predictionsObject.getString("part"));

                            defects = (predictionsObject.getJSONArray("defects"));

                            for (int j = 0;j<defects.length();j++){

                                JSONObject defectsJSONObject = defects.getJSONObject(i);

                                Defect Defect = new Defect();

                                Defect.setBtmy(defectsJSONObject.getString("btmy"));
                                Defect.setBtmx(defectsJSONObject.getString("btmx"));
                                Defect.setLabel(defectsJSONObject.getString("label"));
                                Defect.setTopx(defectsJSONObject.getString("topx"));
                                Defect.setTopy(defectsJSONObject.getString("topy"));

                                defectList.add(Defect);

                            }
                            predictionsList.add(Predictions);

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
                params.put("before_After",state);
                return params;
            }
        };

        queue.add(jsonRequest);

        Intent intent2 = new Intent(this, CompareActivity.class);
        intent2.putExtra("state", state);
        startActivity(intent2);


    }
}
