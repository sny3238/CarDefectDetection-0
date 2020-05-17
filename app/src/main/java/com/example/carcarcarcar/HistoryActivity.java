package com.example.carcarcarcar;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class HistoryActivity extends ListActivity {

    private static final String TAG = "MAIN";


    private String url;
    private RequestQueue queue;
    private Boolean result;
    private ListView lv;

    private String currentrentId, userId, currentcarId, currentrentDate, selectedrentId;
    private JSONArray historyArray, newDefects;

    private TextView currentinfo;

    ArrayList<History> historyList = new ArrayList<History>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lv = (ListView) findViewById(android.R.id.list);
        currentinfo = findViewById(R.id.currentcarinfotextview);

        //Intent intent = getIntent();
        userId = getIntent().getStringExtra("user_id");
        //currentrentId = getIntent().getStringExtra("rent_id");

        //서버
        queue = Volley.newRequestQueue(this);
        String url = Config.getUrl("/viewHistory");
        JSONObject body = new JSONObject();

        try {
            body.put("user_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest jsonRequest1 = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    Boolean result = response.getBoolean("result");
                    if (result) {


                        historyArray = response.getJSONArray("history");

                        //historyList : 사용자에게 보여지는 history 내역 arraylist (jsonarray파싱)

                        for (int i = 0; i < historyArray.length(); i++) {

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
                        for (int i = 0; i < historyList.size(); i++) {
                            if (historyList.get(i).getReturned() == false) {
                                currentrentId = historyList.get(i).getRent_id();
                                currentcarId = historyList.get(i).getCar_id();
                                currentrentDate = historyList.get(i).getRent_date();
                                historyList.remove(i);

                                //현재 이용중인 내역 텍스트 표시
                                currentinfo.setText("대여 시작 날짜 : " + currentrentDate + "\n대여 차량 번호 : " + currentcarId);
                            }
                        }


                    } else {

                        Toast.makeText(getApplicationContext(), "내역이 조회되지 않습니다.", Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jsonRequest1.setTag(TAG);
        queue.add(jsonRequest1);

        HistoryAdapter h_adapter = new HistoryAdapter(this, R.layout.row, historyList);
        setListAdapter(h_adapter);

        lv.setAdapter(h_adapter);

        //리스트뷰 아이템 클릭시
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent apintent = new Intent(HistoryActivity.this, AfterPastHistory.class);
                apintent.putExtra("user_id",userId);
                apintent.putExtra("selected_rentid",historyList.get(position).getRent_id());
                startActivity(apintent);
            }
        });


    }


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

                if (rentdtv != null) {
                    rentdtv.setText("대여 시각 : " + h.getRent_id());
                }
                if (returndtv != null) {
                    returndtv.setText("반납 시각 : " + h.getReturn_date());
                }
                if (caridtv != null) {
                    caridtv.setText("차량 번호 : " + h.getCar_id());
                }
            }

            return v;

        }

    }

    public void onCameraButtonClicked(View v) {
        Intent intent = new Intent(HistoryActivity.this, CameraActivity.class);
        intent.putExtra("rent_id", currentrentId);
        intent.putExtra("user_id", userId);
        intent.putExtra("car_id", currentcarId);
        startActivity(intent);
    }


}
