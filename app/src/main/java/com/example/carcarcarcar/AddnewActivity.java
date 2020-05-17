package com.example.carcarcarcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AddnewActivity extends AppCompatActivity {

    private EditText carIdEditText;
    private Button checkCarTypeButton;
    private TextView displayCarIdTextView;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);

        carIdEditText=findViewById(R.id.carnumber);
        checkCarTypeButton=findViewById(R.id.button2);
        displayCarIdTextView=findViewById(R.id.textView);


        getSupportActionBar().hide();
        //Intent getintent = getIntent();
        //String userid = getintent.getStringExtra("user_id");


        final EditText carnumberEditText = findViewById(R.id.carnumber);
        Spinner spinner = (Spinner) findViewById(R.id.carmodel);
        caridtv = findViewById(R.id.carnumber);
        carid = caridtv.getText().toString();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //Toast.makeText(AddnewActivity.this,"차량 종류를 선택해주세요",Toast.LENGTH_SHORT).show();
                }

                //차량 종류는 일단 small, medium, large 세 가지로 구분
                else if (position == 1) {
                    carmodel = "small";
                } else if (position == 2) {
                    carmodel = "medium";
                } else if (position == 3) {
                    carmodel = "large";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //서버

        queue = Volley.newRequestQueue(this);
        String url = "http://localhost:3000/findCar";

        final JsonObjectRequest jsonRequest1 = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    result = response.getBoolean("result");
                    cartype = response.getString("car_type");
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
                return params;
            }
        };


        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                queue.add(jsonRequest1);

                String url = "http://localhost:3000/startRent";

//                final JsonObjectRequest jsonRequest2 = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            result = response.getBoolean("result");
//                            rentid = response.getString("rent_id");
//                        }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("car_id", carid);
//                        params.put("user_id", userid);
//                        return params;
//                    }
//                };

                Intent intent = new Intent(AddnewActivity.this, CameraActivity.class);

///                 intent.putExtra("user_id",userid);
////                intent.putExtra("car_id",carid);
////                intent.putExtra("rent_id",rentid);

                intent.putExtra("user_id","ewha");
                intent.putExtra("car_id","0000");
                intent.putExtra("rent_id","1234");
                intent.putExtra("state", state);
                startActivity(intent);


//                queue.add(jsonRequest2);

            }
        });


    }

}
