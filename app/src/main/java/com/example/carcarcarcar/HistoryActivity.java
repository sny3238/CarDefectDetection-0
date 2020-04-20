package com.example.carcarcarcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();

        /*
        Listview list = (ListView) findViewById(R.id.history);

        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this, android.R.layout.simple_list_item_1, historyList);
        list.setAdapter(adapter);

        list.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        */


    }

    public void onreturnButtonClicked(View v) {
        Intent intent = new Intent(this, returninfo.class);
        startActivity(intent);
    }
    public void onCameraButtonClicked(View v) {
        Intent intent2 = new Intent(HistoryActivity.this, CameraActivity.class);
        startActivity(intent2);

    }
    public void pasthistoryClicked(View v) {
        Intent intent3 = new Intent(HistoryActivity.this, PastHistoryActivity.class);
        startActivity(intent3);

    }


}
