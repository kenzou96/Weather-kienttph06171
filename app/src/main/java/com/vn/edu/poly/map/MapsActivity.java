package com.vn.edu.poly.map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText edtSearch;
    private Button btnSearch;
    private TextView txtvTenTP;
    private TextView txtvTenQG;
    private ImageView imgIcon;
    private TextView txtvTrangThai;
    private TextView txtvNhietDo;
    private TextView txtvDoAm;
    private TextView txtvGio;
    private TextView txtvlat;
    private TextView txtvlon;
    private Button btnMap;



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
                            txtvTenTP.setText("Thành phố: " + name);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");
                            txtvTrangThai.setText(status);
                            Picasso.with(MapsActivity.this).load("https://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietDo = jsonObjectMain.getString("temp");
                            String doAm = jsonObjectMain.getString("humidity");

                            txtvDoAm.setText("Độ ẩm: " + doAm + "%");
                            txtvNhietDo.setText("Nhiệt độ : " + nhietDo + " °C");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtvTenQG.setText("Quốc gia: " + country);

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("speed");
                            txtvGio.setText("Tốc độ gió " + gio + " m/s");

                            JSONObject jsonObjectCoord = jsonObject.getJSONObject("coord");
                            String lon = jsonObjectCoord.getString("lon");
                            String lat = jsonObjectCoord.getString("lat");
                            txtvlon.setText(lon);
                            txtvlat.setText(lat);


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
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 double lon = Double.parseDouble(txtvlon.getText().toString());
                 double lat = Double.parseDouble(txtvlat.getText().toString());
                String name = edtSearch.getText().toString();
                LatLng a = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(a).title(name));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(a, 7f));
            }
        });

        // Add a marker in Sydney and move the camera

    }

    private void initView() {
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        txtvTenTP = (TextView) findViewById(R.id.txtvTenTP);
        txtvTenQG = (TextView) findViewById(R.id.txtvTenQG);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        txtvTrangThai = (TextView) findViewById(R.id.txtvTrangThai);
        txtvNhietDo = (TextView) findViewById(R.id.txtvNhietDo);
        txtvDoAm = (TextView) findViewById(R.id.txtvDoAm);
        txtvGio = (TextView) findViewById(R.id.txtvGio);

        txtvlat = (TextView) findViewById(R.id.txtvlat);
        txtvlon = (TextView) findViewById(R.id.txtvlon);

        btnMap = (Button) findViewById(R.id.btnMap);

    }
}