package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import android.net.Uri;
import android.os.Environment;

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

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BeforePastHistory extends AppCompatActivity {
    private File mImageFolder;
    private String carid,rentid,userid,rentdate;

    private String state = "b";

    private String label,part;
    private Integer topx, topy, btmx, btmy;
    private JSONArray predictionsJSON,defects;

    private Boolean result;

    private RequestQueue queue;

    ArrayList<predictions> predictionsList = new ArrayList<predictions>();
    ArrayList<Defects> defectsList = new ArrayList<Defects>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_past_history);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        userid = intent.getStringExtra("user_id");
        carid = intent.getStringExtra("car_id");
        rentid = intent.getStringExtra("rent_id");

        File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        mImageFolder = new File(imageFile, "YOCO");



        ImageView imageview_frontal1 = findViewById(R.id.ff_imageview_compare);
        ImageView imageview_frontal2 = findViewById(R.id.ft_imageview_compare);
        ImageView imageView_profile1 = findViewById(R.id.bf_imageview_compare);
        ImageView imageView_profile2 = findViewById(R.id.bt_imageview_compare);
        ImageView imageView_profile3 = findViewById(R.id.lf_imageview_compare);
        ImageView imageView_profile4 = findViewById(R.id.lb_imageview_compare);
        ImageView imageView_back1 = findViewById(R.id.rf_imageview_compare);
        ImageView imageView_back2 = findViewById(R.id.rb_imageview_compare);

        Image[] carPhotos = new Image [7];
        //이미지 넣기

        String imgname;
        try { //uri 경로의 이미지 파일을 로드


            String newPath = String.valueOf(Paths.get(mImageFolder.getAbsolutePath())) + "/";



            Uri uri1 = Uri.parse("file:///" + newPath + rentid +"_" + "ft_b.png");
            imageview_frontal1.setImageURI(uri1);

            Uri uri2 = Uri.parse("file:///" + newPath + rentid +"_" + "ff_b.png");
            imageview_frontal2.setImageURI(uri2);

            Uri uri3 = Uri.parse("file:///" + newPath + rentid +"_" + "rf_b.png");
            imageView_profile1.setImageURI(uri3);

            Uri uri4 = Uri.parse("file:///" + newPath + rentid +"_" + "rb_b.png");
            imageView_profile2.setImageURI(uri4);

            Uri uri5 = Uri.parse("file:///"+ newPath + rentid +"_" + "bt_b.png");
            imageView_profile3.setImageURI(uri5);

            Uri uri6 = Uri.parse("file:///" + newPath + rentid +"_" + "bf_b.png");
            imageView_profile4.setImageURI(uri6);

            Uri uri7 = Uri.parse("file:///" + newPath + rentid +"_" + "lb_b.png");
            imageView_back1.setImageURI(uri7);

            Uri uri8 = Uri.parse("file:///" + newPath + rentid +"_" + "lf_b.png");
            imageView_back2.setImageURI(uri8);

        }catch (Exception e){
            e.printStackTrace();
        }

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

                                Defects Defects = new Defects();

                                Defects.setBtmy(defectsJSONObject.getString("btmy"));
                                Defects.setBtmx(defectsJSONObject.getString("btmx"));
                                Defects.setLabel(defectsJSONObject.getString("label"));
                                Defects.setTopx(defectsJSONObject.getString("topx"));
                                Defects.setTopy(defectsJSONObject.getString("topy"));

                                defectsList.add(Defects);

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



    }

    public void onCameraButtonClicked(View v){
        Intent intent2 = new Intent(this, CameraActivity.class);
        startActivity(intent2);

    }
}
