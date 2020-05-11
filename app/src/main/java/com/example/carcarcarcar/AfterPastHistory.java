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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AfterPastHistory extends AppCompatActivity {

    private RequestQueue queue;

    private String rentid,beforeafter;

    private Boolean result;
    private String label,part;
    private Integer topx, topy, btmx, btmy;
    private JSONObject predictions,defects;

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
        String url = "http://localhost:3000/yolo";

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    result = response.getBoolean("result");
                    if (result) {
                        predictions = response.getJSONObject("new_defects");
                        part = predictions.getString("part");
                        defects = predictions.getJSONObject("defects");
                        label = defects.getString("label");
                        topx = defects.getInt("topx"); //Number
                        topy = defects.getInt("topy");
                        btmx = defects.getInt("btmx");
                        btmy = defects.getInt("btmy");
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
                params.put("before_after",beforeafter);
                return params;
            }
        };


        queue.add(jsonRequest);

        Intent intent2 = new Intent(this, CompareActivity.class);
        startActivity(intent2);


    }
}
