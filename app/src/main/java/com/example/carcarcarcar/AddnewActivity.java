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
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONException;


public class AddnewActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";

    private TextView cameratextview,cartypetextview;
    private EditText carnumberEditText;
    private Button caridentifybtn;
    private ImageButton camerabtn;

    private RequestQueue queue;

    //private String carid;
    //private String userid;
    //private String rentid;


    //private int state = 0; // 0: before   1 : after

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        //getSupportActionBar().hide();

        caridentifybtn = findViewById(R.id.caridentifybutton); //caridentifybtn.setVisibility(View.VISIBLE);
        camerabtn = findViewById(R.id.camerabutton); camerabtn.setVisibility(View.INVISIBLE);
        cameratextview = findViewById(R.id.textView7); cameratextview.setVisibility(View.INVISIBLE);
        carnumberEditText = findViewById(R.id.carnumber);
        cartypetextview=findViewById(R.id.cartypetextview);


        queue = Volley.newRequestQueue(this);

        caridentifybtn.setOnClickListener(new View.OnClickListener(){ //차량 정보 조회하기 누르면
            @Override
            public void onClick(View v) {


                String url = Config.getUrl("/findCar");
                JSONObject body = new JSONObject();
                try{
                    body.put("car_id",cartypetextview.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JsonObjectRequest jsonRequest1 = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean result = response.getBoolean("result");
                            if (result) {

                                //차량 정보 받아옴
                                String cartype = response.getString("car_type");
                                cartypetextview.setText("차량 종류 : "+cartype);

                                //차량 정보 확인 숨기고 카메라 버튼 visible
                                caridentifybtn.setVisibility(View.INVISIBLE);
                                camerabtn.setVisibility(View.VISIBLE);
                                cameratextview.setVisibility(View.VISIBLE);


                            }else{
                                cartypetextview.setText("차량이 존재하지 않거나 대여중입니다.");
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
                }) ;
                jsonRequest1.setTag(TAG);
                queue.add(jsonRequest1);
            }
        });

/*
        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddnewActivity.this, CameraActivity.class);
                Intent getintent = getIntent();
                userid = getintent.getStringExtra("user_id");
                carid = caridtextview.getText().toString();

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
*/

    }

}
