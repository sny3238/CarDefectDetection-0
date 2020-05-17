package com.example.carcarcarcar;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends ListActivity {

    private String url;
    private RequestQueue queue;
    private Boolean result;

    private String currentrentId, userId, currentcarId,currentrentDate;
    private JSONArray historyArray, newDefects;


    ArrayList<History> historyList = new ArrayList<History>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        Intent intent = getIntent();
        userId = getIntent().getStringExtra("user_id");
        //currentrentId = getIntent().getStringExtra("rent_id");

        //서버
        queue = Volley.newRequestQueue(this);
        String url = "http://localhost:3000/viewHistory";



        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    result = response.getBoolean("result");
                    Toast.makeText(getApplicationContext(), "get response from server", Toast.LENGTH_LONG).show();
                    if (result) {

                        historyArray = response.getJSONArray("history");

                        //historyList : 사용자에게 보여지는 history 내역 arraylist (jsonarray파싱)

                        for (int i = 0 ; i<historyArray.length();i++){

                            JSONObject historyObject = historyArray.getJSONObject(i);
                            History history = new History();
                            history.setCar_id(historyObject.getString("car_id"));
                            history.setRent_date(historyObject.getString("rent_date"));
                            history.setRent_id(historyObject.getString("rent_id"));
                            history.setReturn_date(historyObject.getString("return_date"));
                            history.setReturned(historyObject.getBoolean("returned"));
                            historyList.add(history);
                        }

                        //현재 이용중인 내역과 완료된 내역 구분
                        //returned 값 false인거(반납x) 찾아서 current_에 저장하고 historyList에서 삭제
                        for(int i = 0; i<historyList.size();i++){
                            if (historyList.get(i).getReturned() == false){
                                currentrentId = historyList.get(i).getRent_id();
                                currentcarId = historyList.get(i).getCar_id();
                                currentrentDate = historyList.get(i).getRent_date();
                                historyList.remove(i);
                            }
                        }

                    } else {
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
                params.put("user_id", userId);
                Toast.makeText(getApplicationContext(), "send request to server", Toast.LENGTH_LONG).show();
                return params;
            }
        };

        queue.add(jsonRequest);



        //리스트뷰

        class HistoryAdapter extends ArrayAdapter<History> {
            private ArrayList<History> items;

            public HistoryAdapter(Context context, int textViewResourceId, ArrayList<History> items) {
                super(context, textViewResourceId, items);
                this.items = items;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.row, null);
                }
                History h = items.get(position);
                if (h != null) {

                    TextView rentdtv = (TextView) v.findViewById(R.id.rentdatetext);
                    TextView returndtv = (TextView) v.findViewById(R.id.returndatetxt);
                    TextView caridtv = (TextView) v.findViewById(R.id.caridtext);

                    if(rentdtv != null){
                        rentdtv.setText("대여 시각 : " + h.getRent_id());
                    }
                    if (returndtv != null) {
                        returndtv.setText("반납 시각 : " + h.getReturn_date());
                    }
                    if (caridtv != null){
                        caridtv.setText("차량 번호 : " + h.getCar_id());
                    }
                }
                return v;
            }

        }

        HistoryAdapter h_adapter = new HistoryAdapter(this, R.layout.row, historyList);
       setListAdapter(h_adapter);
        ListView lv = (ListView)findViewById(R.id.list);
        lv.setAdapter(h_adapter);

    }


    public void onreturnButtonClicked(View v) {
        Intent intent = new Intent(this, BeforePastHistory.class);
        intent.putExtra("rent_id",currentrentId);
        intent.putExtra("return_date",currentrentDate);
        intent.putExtra("car_id",currentcarId);
        //intent.putExtra("new_defects",newDefects);
        startActivity(intent);
    }

    public void onCameraButtonClicked(View v) {
        Intent intent = new Intent(HistoryActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    public void pasthistorylistClicked(View v) {
        Intent intent = new Intent(HistoryActivity.this, AfterPastHistory.class);
        //리스트뷰 클릭시 인텐트 전달, 과거 내역 표시
        startActivity(intent);
    }
}
