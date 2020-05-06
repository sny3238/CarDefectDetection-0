package com.example.carcarcarcar.ui.login;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import com.example.carcarcarcar.CameraActivity;
import com.example.carcarcarcar.HistoryActivity;
import com.example.carcarcarcar.MenuActivity;
import com.example.carcarcarcar.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";

    private RequestQueue queue;

    private Boolean result;

    private EditText usernameEditText = findViewById(R.id.username);
    private EditText passwordEditText = findViewById(R.id.password);
    private Button loginButton = findViewById(R.id.loginBtn);

    private String userid, passwd;

    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        userid = usernameEditText.getText().toString();
        passwd =  passwordEditText.getText().toString();
        final Button loginButton = findViewById(R.id.loginBtn);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        //서버 연결
        queue = Volley.newRequestQueue(this);
        url = "http://localhost:3000/login";

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    result = response.getBoolean("result");
                    Toast.makeText(getApplicationContext(), "get response from server", Toast.LENGTH_LONG).show();


                    if (result) {
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        //Histpry Activity로 user id uri intent 전달
                        Intent intent2 = new Intent(LoginActivity.this, HistoryActivity.class);
                        intent2.putExtra("user_id",userid);
                        startActivity(intent2);

                        //로그인 성공하면 Menu Activity로 전환
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
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
                params.put("user_id", userid);
                params.put("user_password", passwd);
                Toast.makeText(getApplicationContext(), "send request to server", Toast.LENGTH_LONG).show();
                return params;
            }
        };

        jsonRequest.setTag(TAG);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue.add(jsonRequest);
            }
        });


        //퍼미션
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //퍼미션 상태 확인
            if (!hasPermissions(PERMISSIONS)) {

                //퍼미션 허가 안되어있다면 사용자에게 요청
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            } else {

            }
        }

    }

    //버튼만 누르면 넘어가게 임시로 넣은 코드, 최종버전엔 지우기
    public void onfakeloginButtonClicked(View v) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }


    //이 아래로는 모두 퍼미션 관련 코드(수정x)
    static final int PERMISSIONS_REQUEST_CODE = 1000;
    String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private boolean hasPermissions(String[] permissions) {
        int result;

        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (String perms : permissions) {

            result = ContextCompat.checkSelfPermission(this, perms);

            if (result == PackageManager.PERMISSION_DENIED) {
                //허가 안된 퍼미션 발견
                return false;
            }
        }

        //모든 퍼미션이 허가되었음
        return true;
    }

    //퍼미션
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case PERMISSIONS_REQUEST_CODE:

                if (grantResults.length > 0) {
                    boolean cameraPermissionAccepted = grantResults[0]
                            == PackageManager.PERMISSION_GRANTED;
                    boolean diskPermissionAccepted = grantResults[1]
                            == PackageManager.PERMISSION_GRANTED;

                    if (!cameraPermissionAccepted || !diskPermissionAccepted)
                        showDialogForPermission("앱을 실행하려면 접근권한을 허용하셔야합니다.");
                    else {
                        Intent mainIntent = new Intent(this, LoginActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }
                break;
        }
    }

    //퍼미션
    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        builder.create().show();
    }


}
