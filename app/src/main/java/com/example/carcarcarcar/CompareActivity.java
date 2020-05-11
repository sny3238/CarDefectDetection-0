package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

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

public class CompareActivity extends AppCompatActivity {

    private RequestQueue queue;

    private String rentid;
    private Boolean result;

    private String label,part;
    private Number topx, topy, btmx, btmy;
    private JSONObject newDefects,defects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        getSupportActionBar().hide();

        Intent getintent = getIntent();

        //rentid= getintent.getDataString("rent_id");

        String url = "http://localhost:3000/compare";

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    result = response.getBoolean("result");
                    if (result) {
                        newDefects = response.getJSONObject("new_defects");
                        part = newDefects.getString("part");
                        defects = newDefects.getJSONObject("defects");
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
                return params;
            }
        };

        queue.add(jsonRequest);
    }
}
