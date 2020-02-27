package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

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


}
