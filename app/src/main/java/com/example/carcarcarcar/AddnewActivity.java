package com.example.carcarcarcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;


public class AddnewActivity extends AppCompatActivity {
    private static final String TAG = "MAIN";

    private TextView cameratextview,cartypetextview;
    private EditText carnumberEditText;
    private Button caridentifybtn;
    private ImageButton camerabtn;
    private RequestQueue queue;

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
                    body.put("car_id",carnumberEditText.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final JsonObjectRequest jsonRequest1 = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean result = response.getBoolean("result");

                            if (result) {

                                //차량 정보 받아옴
                                String cartype = response.getString("car_type");
                                cartypetextview.setText("차량 대여가 가능합니다.\n차량 종류 : "+cartype);

                                //차량 정보 확인 숨기고 카메라 버튼 visible

                                camerabtn.setVisibility(View.VISIBLE);
                                cameratextview.setVisibility(View.VISIBLE);

                            }else{


                                JSONObject body=new JSONObject();
                                try{
                                    Intent getIntent=getIntent();
                                    String userid=getIntent.getStringExtra("user_id");
                                    body.put("user_id",userid);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                final JsonObjectRequest jsonRequest3=new JsonObjectRequest(Request.Method.POST, Config.getUrl("/checkUserInfo"), body, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {

                                            JSONObject user_info=response.getJSONObject("user_info");
                                            if(user_info.getBoolean("renting")){

                                                cartypetextview.setText("대여중인 차량이 존재합니다.");
                                                JSONObject photos_state=user_info.getJSONObject("photos_state");
                                                Boolean photos_state_before=photos_state.getBoolean("before");

                                                //Toast.makeText(getApplicationContext(), photos_state.toString(), Toast.LENGTH_LONG).show();
                                                if(photos_state_before){

                                                    Intent intent=new Intent(AddnewActivity.this, BeforePastHistory.class);
                                                    startActivity(intent);

                                                }else{
                                                    Intent intent=new Intent(AddnewActivity.this,CameraActivity.class);
                                                    startActivity(intent);
                                                }
                                            }else{
                                                cartypetextview.setText("차량 존재하지 않거나 대여중입니다");
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                jsonRequest3.setTag(TAG);
                                queue.add(jsonRequest3);



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


        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent getintent = getIntent();
                String userid = getintent.getStringExtra("user_id");
                String carid = carnumberEditText.getText().toString();
                JSONObject body=new JSONObject();
                try{
                    body.put("user_id",userid);
                    body.put("car_id",carid);
                }catch(JSONException e){
                    e.printStackTrace();
                }
                String url=Config.getUrl("/startRent");

                final JsonObjectRequest jsonRequest2=new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean result=response.getBoolean("result");
                            if (result){

                                Intent intent = new Intent(AddnewActivity.this, CameraActivity.class);
                                Intent getintent = getIntent();
                                String userid = getintent.getStringExtra("user_id");
                                String carid = carnumberEditText.getText().toString();
                                String rentid = response.getString("rent_id");

                                intent.putExtra("rent_id",rentid);
                                intent.putExtra("user_id",userid);
                                intent.putExtra("car_id",carid);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "차량을 대여할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) ;

                jsonRequest2.setTag(TAG);
                queue.add(jsonRequest2);


            }
        });


    }

}
