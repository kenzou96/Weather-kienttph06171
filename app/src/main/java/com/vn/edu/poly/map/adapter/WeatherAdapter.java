package com.vn.edu.poly.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vn.edu.poly.map.MapsActivity;
import com.vn.edu.poly.map.R;
import com.vn.edu.poly.map.moldel.Weather;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter {

    Context context;
    ArrayList<Weather> weatherList;
    private ImageView imgIcon;
    private TextView txtvDay;
    private TextView txtvTrangThai;
    private TextView txtvNhietDoMax;
    private TextView txtvNhietDoMin;

    public WeatherAdapter(Context context, ArrayList<Weather> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.iem_sevenday, null);

        Weather weather = weatherList.get(position);

        txtvDay = convertView.findViewById(R.id.txtvDay);
        txtvTrangThai = convertView.findViewById(R.id.txtvTrangThai);
        imgIcon = convertView.findViewById(R.id.imgIcon);
        txtvNhietDoMin = convertView.findViewById(R.id.txtvNhietDoMin);
        txtvNhietDoMax = convertView.findViewById(R.id.txtvNhietDoMax);



        txtvDay.setText(weather.ngay);
        txtvTrangThai.setText(weather.trangThai+" , ");
        txtvNhietDoMin.setText("Nhiệt độ: "+weather.minND+" - ");
        txtvNhietDoMax.setText(weather.maxND+"°C");
        Picasso.with(context).load("https://openweathermap.org/img/w/" + weather.icon + ".png").into(imgIcon);

        return convertView;
    }


}
