package com.example.carcarcarcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class returninfo extends AppCompatActivity {

    //ImageView imgView[] = new ImageView[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returninfo);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        ArrayList<String> data = (ArrayList<String>) intent.getSerializableExtra("savedImgList");

    }
    public void onCameraButtonClicked(View v){
        Intent intent2 = new Intent(returninfo.this, CameraActivity.class);
        startActivity(intent2);


    }
}
