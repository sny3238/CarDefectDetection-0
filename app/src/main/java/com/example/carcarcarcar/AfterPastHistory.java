package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AfterPastHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_past_history);
        getSupportActionBar().hide();
    }
    public void onCompareButtonClicked(View v){
        Intent intent2 = new Intent(this, CompareActivity.class);
        startActivity(intent2);
    }
}
