package com.example.carcarcarcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();

        Intent getIntent = getIntent();
        Bundle bundle = getIntent().getExtras();
        userid = getIntent.getStringExtra("user_id");


    }
    public void onaddnewButtonClicked(View v) {
        Intent intent = new Intent(this, AddnewActivity.class);
        intent.putExtra("user_id",userid);
        startActivity(intent);
    }
    public void onprevlistButtonClicked(View v) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("user_id",userid);
        startActivity(intent);
    }
}
