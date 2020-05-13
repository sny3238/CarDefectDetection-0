package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.net.Uri;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class BeforePastHistory extends AppCompatActivity {
    private File mImageFolder;
    private String carid,rentid,userid,rentdate;

    private Boolean result;

    private static final String TAG = "MAIN";

    private RequestQueue queue;

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



        ImageView imageview_frontal1 = findViewById(R.id.imageview_frontal1);
        ImageView imageview_frontal2 = findViewById(R.id.imageview_frontal2);
        ImageView imageView_profile1 = findViewById(R.id.imageview_profile1);
        ImageView imageView_profile2 = findViewById(R.id.imageview_profile2);
        ImageView imageView_profile3 = findViewById(R.id.imageview_profile3);
        ImageView imageView_profile4 = findViewById(R.id.imageview_profile4);
        ImageView imageView_back1 = findViewById(R.id.imageview_back1);
        ImageView imageView_back2 = findViewById(R.id.imageview_back2);

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
        String url = "http://localhost:3000/test_respond";
        JSONObject body = new JSONObject();


        try {
            body.put("before_after", "before");
            body.put("rent_id",rentid);
            body.put("car+photos",carPhotos);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    result = response.getBoolean("result");
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonRequest.setTag(TAG);




    }
    /*public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }*/

    public void onCameraButtonClicked(View v){
        Intent intent2 = new Intent(this, CameraActivity.class);
        startActivity(intent2);

    }
}
