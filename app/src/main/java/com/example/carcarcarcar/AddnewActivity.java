package com.example.carcarcarcar;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AddnewActivity extends AppCompatActivity {

    String carmodel; //carmodle 차량 종류 입력받음
    String carnum; // carnum 차량 번호 입력받음

    //현재 날짜를 string으로 처리 (임시)
    Date date = new Date();
    SimpleDateFormat forma = new SimpleDateFormat("yyyy-MM-dd");

    String currentdate = forma.format(date); //currentdate에 yyyy-mm-dd 형식으로 날짜 저장
    private TextView carid;
    private RequestQueue queue;
    private ImageButton camerabtn;

    private String cartype;
    private Boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);

        getSupportActionBar().hide();

        final EditText carnumberEditText = findViewById(R.id.carnumber);
        Spinner spinner = (Spinner) findViewById(R.id.carmodel);
        carid = findViewById(R.id.carnumber);

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

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
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
                params.put("car_id", carid.getText().toString());
                return params;
            }
        };

        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue.add(jsonRequest);

                Intent intent = new Intent(AddnewActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });


    }

}
