package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Date;

import java.text.SimpleDateFormat;


public class AddnewActivity extends AppCompatActivity {

    String carmodel; //carmodle 차량 종류 입력받음
    String carnum; // carnum 차량 번호 입력받음

    //현재 날짜를 string으로 처리 (임시)
    Date date = new Date();
    SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd");

    String currentdate = forma.format(date); //currentdate에 yyyy-mm-dd 형식으로 날짜 저장



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        getSupportActionBar().hide();
        final EditText carnumberEditText = findViewById(R.id.carnumber);
        Spinner spinner = (Spinner)findViewById(R.id.carmodel);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Toast.makeText(AddnewActivity.this,"차량 종류를 선택해주세요",Toast.LENGTH_SHORT).show();
                }

                //차량 종류는 일단 small, medium, large 세 가지로 구분
                else if (position==1){
                    carmodel="small";                }
                else if (position==2){
                    carmodel = "medium";                }
                else if (position==3){
                    carmodel = "large";                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        carnum = carnumberEditText.getText().toString(); //carnum에 입력받은 차 번호 저장


    }

    public void onprevlistButtonClicked(View v) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
    public void onCameraButtonClicked(View v) {
        Intent intent2 = new Intent(AddnewActivity.this, CameraActivity.class);
        startActivity(intent2);


    }
}
