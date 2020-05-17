package com.example.carcarcarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ComparePopup extends AppCompatActivity {


    ImageView before, after;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_popup);
        getSupportActionBar().hide();

        before = findViewById(R.id.before);
        after = findViewById(R.id.after);
        txt = findViewById(R.id.comparetextView);

        Intent intent = getIntent();
        String part= intent.getStringExtra("part");

        switch (part) {
            case "ff":
                txt.setText("전면 상단부");
                //사진 불러오고 좌표값으로 박스 표시
                //before.setImageURI();
                //after.setImageURI();
                break;
            //case 7개 더 복붙


        }

        //txt.setText(data);

    }

    //확인 버튼 클릭릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
}
