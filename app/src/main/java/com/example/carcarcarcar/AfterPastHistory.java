package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

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

    private String rentid, userid, carid;

    private Boolean result;
    private String label,part;
    private Integer topx, topy, btmx, btmy;
    private JSONObject predictions,defects;

    private int state = 1;  // 0 : before  1 : after

    private Button compareBtn;
    private Button yoloBtn;

    private boolean yolo_done = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_past_history);
        getSupportActionBar().hide();

        Intent saving_intent = getIntent();
        rentid=getIntent().getStringExtra("rent_id");
        userid = getIntent().getStringExtra("user_id");
        carid = getIntent().getStringExtra("car_id");
        Switch switch1 = findViewById(R.id.switch1);
        compareBtn = findViewById(R.id.compareBtn);
        yoloBtn = findViewById(R.id.yoloBtn);

        compareBtn.setEnabled(false);

        final Intent serviceIntent_compare = new Intent(this, YOLOService.class);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                    yolo_done = false;
                else{
                    yolo_done = true;

                    serviceIntent_compare.putExtra("YOLO_done", yolo_done); // send false

                    startService(serviceIntent_compare);

                    yoloBtn.setEnabled(false);
                    compareBtn.setEnabled(true);
                }

            }
        });





    }

    public void onYOLOButtonClicked(View v){
        Intent serviceIntent_yolo = new Intent(this, YOLOService.class);
        serviceIntent_yolo.putExtra("YOLO_done", yolo_done); // send false

        startService(serviceIntent_yolo);



//        String url = "http://localhost:3000/yolo";
//
//        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    result = response.getBoolean("result");
//                    if (result) {
//                        predictions = response.getJSONObject("new_defects");
//                        part = predictions.getString("part");
//                        defects = predictions.getJSONObject("defects");
//                        label = defects.getString("label");
//                        topx = defects.getInt("topx"); //Number
//                        topy = defects.getInt("topy");
//                        btmx = defects.getInt("btmx");
//                        btmy = defects.getInt("btmy");
//                    } else {
//                        return;
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("rent_id", rentid);
//                params.put("before_after",beforeafter);
//                return params;
//            }
//        };
//
//
//        queue.add(jsonRequest);
//
//        Intent intent2 = new Intent(this, CompareActivity.class);
//        intent2.putExtra("state", state);
//        startActivity(intent2);


    }

    public void onCompareButtonClicked(View v){
        stopService(new Intent(this, YOLOService.class));
        Intent intent2 = new Intent(this, CompareActivity.class);
        //intent2.putExtra("state", state);
        startActivity(intent2);
    }




}