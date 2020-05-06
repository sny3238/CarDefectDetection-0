package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;

public class BeforePastHistory extends AppCompatActivity {
    private File mImageFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_past_history);
        getSupportActionBar().hide();

        ImageView imageview_frontal1 = findViewById(R.id.imageview_frontal1);
        ImageView imageview_frontal2 = findViewById(R.id.imageview_frontal2);
        ImageView imageView_profile1 = findViewById(R.id.imageview_profile1);
        ImageView imageView_profile2 = findViewById(R.id.imageview_profile2);
        ImageView imageView_profile3 = findViewById(R.id.imageview_profile3);
        ImageView imageView_profile4 = findViewById(R.id.imageview_profile4);
        ImageView imageView_back1 = findViewById(R.id.imageview_back1);
        ImageView imageView_back2 = findViewById(R.id.imageview_back2);

        try { //uri 경로의 이미지 파일을 로드
            String newPath = String.valueOf(Paths.get(mImageFolder.getAbsolutePath()));

            Uri uri1 = Uri.parse("file:///" + Environment.getExternalStorageDirectory() + newPath + "front_top.jpg");
            imageview_frontal1.setImageURI(uri1);

            Uri uri2 = Uri.parse("file:///" + Environment.getExternalStorageDirectory() + newPath + "front_front.jpg");
            imageview_frontal2.setImageURI(uri2);

            Uri uri3 = Uri.parse("file:///" + Environment.getExternalStorageDirectory() + newPath + "right_front.jpg");
            imageView_profile1.setImageURI(uri3);

            Uri uri4 = Uri.parse("file:///" + Environment.getExternalStorageDirectory() + newPath + "right_back.jpg");
            imageView_profile2.setImageURI(uri4);

            Uri uri5 = Uri.parse("file:///" + Environment.getExternalStorageDirectory() + newPath + "back_top.jpg");
            imageView_profile3.setImageURI(uri5);

            Uri uri6 = Uri.parse("file:///" + Environment.getExternalStorageDirectory() + newPath + "back_front.jpg");
            imageView_profile4.setImageURI(uri6);

            Uri uri7 = Uri.parse("file:///" + Environment.getExternalStorageDirectory() + newPath + "left_back.jpg");
            imageView_back1.setImageURI(uri7);

            Uri uri8 = Uri.parse("file:///" + Environment.getExternalStorageDirectory() + newPath + "left_front.jpg");
            imageView_back2.setImageURI(uri8);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onCameraButtonClicked(View v){
        Intent intent2 = new Intent(this, CameraActivity.class);
        startActivity(intent2);
    }
}
