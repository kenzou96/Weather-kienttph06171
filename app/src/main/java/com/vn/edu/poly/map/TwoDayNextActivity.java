package com.vn.edu.poly.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vn.edu.poly.map.adapter.WeatherAdapter;
import com.vn.edu.poly.map.moldel.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TwoDayNextActivity extends AppCompatActivity {

    private ListView lv7day;
    WeatherAdapter weatherAdapter;
    ArrayList<Weather> weatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day_next);
        Intent intent = getIntent();
        String city = intent.getStringExtra("tenTP");
        Log.d("kqtruyen", "kq truyen dl: " + city);

        get7DayData(city);
        initView();
    }

    private void get7DayData(final String data) {
        String url = "https://api.openweathermap.org/data/2.5/forecast/?q=" + data + "&units=metric&cnt=24&appid=bcfd474bf493e3a37251b7d238e947b3";
        RequestQueue requestQueue = Volley.newRequestQueue(TwoDayNextActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("kq", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            JSONObject jsonObjectCity = new JSONObject("city");
//                            String name = jsonObjectCity.getString("name");

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for(int i=16 ; i<jsonArrayList.length();i++){
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt");
                                long l = Long.valueOf(ngay);
                                Date date = new Date(l*1000L);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                                String day = dateFormat.format(date);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                                String min = jsonObjectTemp.getString("temp_min");
                                String max = jsonObjectTemp.getString("temp_max");

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");

                                weatherList.add(new Weather(day,status,icon,min,max));
                            }
                            weatherAdapter.notifyDataSetChanged();
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
        requestQueue.add(stringRequest);
    }

    private void initView() {
        lv7day = (ListView) findViewById(R.id.lv7day);
        weatherList = new ArrayList<Weather>();
        weatherAdapter = new WeatherAdapter(TwoDayNextActivity.this,weatherList);
        lv7day.setAdapter(weatherAdapter);
    }
}