package com.example.carcarcarcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddnewActivity extends AppCompatActivity {

    String carmodel; //carmodle 차량 종류 입력받음
    String carnum; // carnum 차량 번호 입력받음

    //현재 날짜를 string으로 처리 (임시)
    Date date = new Date();
    SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd");

    String currentdate = forma.format(date); //currentdate에 yyyy-mm-dd 형식으로 날짜 저장

    private TextView caridtextview,cameratextview;
    private EditText carnumberEditText;
    private RequestQueue queue;

    private Button caridentifybtn;
    private ImageButton camerabtn;
    private String carid;

    private String cartype;
    private Boolean result;
    private String rentid;
    private String userid;

    private int state = 0; // 0: before   1 : after

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);

        getSupportActionBar().hide();
        Intent getintent = getIntent();
        userid = getintent.getStringExtra("user_id");

        //Spinner spinner = (Spinner) findViewById(R.id.);

        caridentifybtn = findViewById(R.id.caridentifybutton);
        caridentifybtn.setVisibility(View.VISIBLE);
        //처음에는 카메라 버튼 숨김
        camerabtn = findViewById(R.id.camerabutton);
        camerabtn.setVisibility(View.INVISIBLE);

        cameratextview = findViewById(R.id.textView7);
        cameratextview.setVisibility(View.INVISIBLE);

        caridtextview = findViewById(R.id.carnumber);
        carid = caridtextview.getText().toString();

        //스피너

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    //Toast.makeText(AddnewActivity.this,"차량 종류를 선택해주세요",Toast.LENGTH_SHORT).show();
//                }
//
//                //차량 종류는 일단 small, medium, large 세 가지로 구분
//                else if (position == 1) {
//                    carmodel = "small";
//                } else if (position == 2) {
//                    carmodel = "medium";
//                } else if (position == 3) {
//                    carmodel = "large";
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//            });

        queue = Volley.newRequestQueue(this);

        caridentifybtn.setOnClickListener(new View.OnClickListener(){ //차량 정보 조회하기 누르면
            @Override
            public void onClick(View v) {

                //서버
                String url = "http://localhost:3000/findCar";
                //차량 정보 확인
                final JsonObjectRequest jsonRequest1 = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            result = response.getBoolean("result");
                            if (result) {

                                //차량 정보 받아옴
                                cartype = response.getString("car_type");

                                //차량 정보 확인 숨기고 카메라 버튼 visible
                                caridentifybtn.setVisibility(View.INVISIBLE);
                                camerabtn.setVisibility(View.VISIBLE);
                                cameratextview.setVisibility(View.VISIBLE);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("car_id", carid);
                        params.put("user_id", userid);
                        return params;
                    }
                };
                queue.add(jsonRequest1);
            }
        });


        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddnewActivity.this, CameraActivity.class);

                intent.putExtra("user_id",userid);
                intent.putExtra("car_id",carid);
                intent.putExtra("rent_id",rentid);

//                intent.putExtra("user_id","ewha");
//                intent.putExtra("car_id","0000");
//                intent.putExtra("rent_id","1234");
//                intent.putExtra("state", state);

                startActivity(intent);

            }
        });


    }

}
