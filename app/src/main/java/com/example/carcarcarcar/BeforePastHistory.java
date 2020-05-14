package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.android.volley.DefaultRetryPolicy;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class BeforePastHistory extends AppCompatActivity {
    private File mImageFolder;
    private String carid,rentid,userid,rentdate;

    private Boolean result;

    private static final String TAG = "MAIN";

    private RequestQueue queue;

    String url = "http://10.200.149.1:3000/uploadPhoto";

    String[] imagelist;

    String[] part_list = {"ft", "ff", "rf", "rb", "bt", "bf", "lb", "lf"};

    JSONArray imageArray;

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

        imagelist = new String[8];
        //이미지 넣기
        Bitmap[] bitmaplist = new Bitmap[8];

        String imgname;
        try { //uri 경로의 이미지 파일을 로드


            String newPath = String.valueOf(Paths.get(mImageFolder.getAbsolutePath())) + "/";



            Uri uri1 = Uri.parse("file:///" + newPath + rentid +"_" + "ft_b.png");
            imageview_frontal1.setImageURI(uri1);
            bitmaplist[0] = MediaStore.Images.Media.getBitmap(
                    getApplicationContext().getContentResolver(), uri1);


            Uri uri2 = Uri.parse("file:///" + newPath + rentid +"_" + "ff_b.png");
            imageview_frontal2.setImageURI(uri2);
            bitmaplist[1] = MediaStore.Images.Media.getBitmap(
                    getApplicationContext().getContentResolver(), uri2);

            Uri uri3 = Uri.parse("file:///" + newPath + rentid +"_" + "rf_b.png");
            imageView_profile1.setImageURI(uri3);
            bitmaplist[2] = MediaStore.Images.Media.getBitmap(
                    getApplicationContext().getContentResolver(), uri3);

            Uri uri4 = Uri.parse("file:///" + newPath + rentid +"_" + "rb_b.png");
            imageView_profile2.setImageURI(uri4);
            bitmaplist[3] = MediaStore.Images.Media.getBitmap(
                    getApplicationContext().getContentResolver(), uri4);

            Uri uri5 = Uri.parse("file:///"+ newPath + rentid +"_" + "bt_b.png");
            imageView_profile3.setImageURI(uri5);
            bitmaplist[4] = MediaStore.Images.Media.getBitmap(
                    getApplicationContext().getContentResolver(), uri5);

            Uri uri6 = Uri.parse("file:///" + newPath + rentid +"_" + "bf_b.png");
            imageView_profile4.setImageURI(uri6);
            bitmaplist[5] = MediaStore.Images.Media.getBitmap(
                    getApplicationContext().getContentResolver(), uri6);

            Uri uri7 = Uri.parse("file:///" + newPath + rentid +"_" + "lb_b.png");
            imageView_back1.setImageURI(uri7);
            bitmaplist[6] = MediaStore.Images.Media.getBitmap(
                    getApplicationContext().getContentResolver(), uri7);

            Uri uri8 = Uri.parse("file:///" + newPath + rentid +"_" + "lf_b.png");
            imageView_back2.setImageURI(uri8);
            bitmaplist[7] = MediaStore.Images.Media.getBitmap(
                    getApplicationContext().getContentResolver(), uri8);

        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i=0; i<8; i++){
            imagelist[i] = getStringImage(bitmaplist[i]);
        }

        SendImage(imagelist);






    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void SendImage(final String[] images){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("before_after", "b");
            jsonObject.put("rent_id",rentid);
            imageArray = new JSONArray();

            for(int i=0; i<8; i++){
                JSONObject imageObject = new JSONObject();
                imageObject.put("part", part_list[i]);
                imageObject.put("image", images[i]);
                imageArray.put(imageObject);
            }
            jsonObject.put("images", imageArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            result = response.getBoolean("result");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BeforePastHistory.this, "No internet connection", Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                params.put("before_after", "b");
                params.put("rent_id", rentid);
                params.put("images", imageArray.toString());
                return params;
            }
        };
        {
            int socketTimeout = 50000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }
    }

    public void onCameraButtonClicked(View v){
        Intent intent2 = new Intent(this, CameraActivity.class);
        startActivity(intent2);

    }


}
