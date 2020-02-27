package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();

        Intent getIntent = getIntent();
        Bundle bundle = getIntent().getExtras();

    }
    public void onaddnewButtonClicked(View v) {
        Intent intent = new Intent(this, AddnewActivity.class);
        startActivity(intent);
    }
    public void onprevlistButtonClicked(View v) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
