package com.vn.edu.poly.map;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.pavlospt.CircleView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText edtSearch;
    private ImageButton btnSearch;
    private TextView txtvTenTP;
    private TextView txtvTenQG;
    private ImageView imgIcon;
    private TextView txtvTrangThai;
    private CircleView txtvNhietDo;
    private TextView txtvDoAm;
    private TextView txtvGio;
//    private ImageButton btnXem;
    private ImageButton btnHD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initView();

    }


    private void getData(String data) {

        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=bcfd474bf493e3a37251b7d238e947b3";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ketqua", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String name = jsonObject.getString("name");


                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Log.d("trangthai", "trang thai la: " + status);
                            Log.d("icon", icon);
                            switch (status) {
                                case "Rain":
                                    txtvTrangThai.setText("Trời mưa");
                                    break;
                                case "Clouds":
                                    txtvTrangThai.setText("Trời nhiều mây");
                                    break;
                                case "Clear":
                                    txtvTrangThai.setText("Trời quang");
                                    break;
                                case "Mist":
                                    txtvTrangThai.setText("Sương mù");
                                    break;
                                case "Haze":
                                    txtvTrangThai.setText("Sương mù");
                                    break;
                                default:
                                    txtvTrangThai.setText(status);
                            }

                            switch (icon) {

                                case "01d":
                                    imgIcon.setImageResource(R.drawable.mattroi);
                                    break;
                                case "02d":
                                    imgIcon.setImageResource(R.drawable.mattroi);
                                    break;
                                case "03d":
                                    imgIcon.setImageResource(R.drawable.cloudy);
                                    break;
                                case "04d":
                                    imgIcon.setImageResource(R.drawable.cloud);
                                    break;
                                case "03n":
                                    imgIcon.setImageResource(R.drawable.cloud);
                                    break;
                                case "09d":
                                    imgIcon.setImageResource(R.drawable.rain);
                                    break;
                                case "10d":
                                    imgIcon.setImageResource(R.drawable.may_mua_mattroi);
                                    break;

                                case "04n":
                                    imgIcon.setImageResource(R.drawable.cloudy);
                                    break;
                                case "01n":
                                    imgIcon.setImageResource(R.drawable.moon);
                                    break;
                                case "02n":
                                    imgIcon.setImageResource(R.drawable.moon);
                                    break;
                                case "09n":
                                    imgIcon.setImageResource(R.drawable.rain);
                                    break;
                                //may mua mat trang

                                case "10n":
                                    imgIcon.setImageResource(R.drawable.rain);
                                    break;
                                case "50n":
                                    imgIcon.setImageResource(R.drawable.fog);
                                    break;
                                case "50d":
                                    imgIcon.setImageResource(R.drawable.fog);
                                    break;

                                default:
                                    Picasso.with(MapsActivity.this).load("https://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);
                            }


//                            if(icon.equalsIgnoreCase("10d")){
//                                imgIcon.setImageResource(R.drawable.may_mua_mattroi);
//                            }
//                            else if(icon.equalsIgnoreCase("01d")){
//                                imgIcon.setImageResource(R.drawable.mattroi);
//                            }
//                            else if(icon.equalsIgnoreCase("50n")){
//                                imgIcon.setImageResource(R.drawable.fog);
//                            }
//                            else {
//                                Picasso.with(MapsActivity.this).load("https://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);
//                            }


                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietDo = jsonObjectMain.getString("temp");
                            String doAm = jsonObjectMain.getString("humidity");

                            txtvDoAm.setText(doAm + "%");
                            txtvNhietDo.setTitleText(nhietDo + " °C");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtvTenTP.setText("Thành phố: " + name + " , " + country);

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("speed");
                            txtvGio.setText(gio + " m/s");

                            JSONObject jsonObjectCoord = jsonObject.getJSONObject("coord");
                            double lon = jsonObjectCoord.getDouble("lon");
                            double lat = jsonObjectCoord.getDouble("lat");

                            String ten = edtSearch.getText().toString();
                            LatLng a = new LatLng(lat, lon);
                            mMap.addMarker(new MarkerOptions().position(a).title(ten));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(a, 8f));


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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                getData(city);

            }
        });
//        btnXem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String city = edtSearch.getText().toString();
//                Intent intent = new Intent(MapsActivity.this, ListWeatherActivity.class);
//                intent.putExtra("tenTP", city);
//                startActivity(intent);
//
//            }
//        });
        btnHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MapsActivity.this);
                dialog.setTitle("Hướng dẫn");
                dialog.setContentView(R.layout.dialog_hd);
                dialog.show();
            }
        });

        // Add a marker in Sydney and move the camera

    }

    private void initView() {
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        txtvTenTP = (TextView) findViewById(R.id.txtvTenTP);
//        txtvTenQG = (TextView) findViewById(R.id.txtvTenQG);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        txtvTrangThai = (TextView) findViewById(R.id.txtvTrangThai);
        txtvNhietDo = findViewById(R.id.txtvNhietDo);
        txtvDoAm = (TextView) findViewById(R.id.txtvDoAm);
        txtvGio = (TextView) findViewById(R.id.txtvGio);
//        btnXem = findViewById(R.id.btnXem);

        btnHD = (ImageButton) findViewById(R.id.btnHD);

    }
    public void showAlertListDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] lop = {"24h tới","48h tới","72h tới","96h tới", "120h tới"};
        builder.setTitle("Dự báo");
        builder.setItems(lop, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String city = edtSearch.getText().toString();
                if(i==0) {
                    Intent intent = new Intent(MapsActivity.this, ListWeatherActivity.class);
                    intent.putExtra("tenTP", city);
                    startActivity(intent);
                }
                else if(i==1) {
                    Intent intent = new Intent(MapsActivity.this, OneDayNextActivity.class);
                    intent.putExtra("tenTP", city);
                    startActivity(intent);
                }
                else if(i==2) {
                    Intent intent = new Intent(MapsActivity.this, TwoDayNextActivity.class);
                    intent.putExtra("tenTP", city);
                    startActivity(intent);
                }
                else if(i==3) {
                    Intent intent = new Intent(MapsActivity.this, ThreeDayNextActivity.class);
                    intent.putExtra("tenTP", city);
                    startActivity(intent);
                }
                else if(i==4) {
                    Intent intent = new Intent(MapsActivity.this, FourDayNextActivity.class);
                    intent.putExtra("tenTP", city);
                    startActivity(intent);
                }

                Toast.makeText(MapsActivity.this, lop[i], Toast.LENGTH_SHORT).show();

            }
        });

        builder.show();
    }
}