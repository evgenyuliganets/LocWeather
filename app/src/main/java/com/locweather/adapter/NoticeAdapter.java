package com.locweather.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;

import com.google.android.gms.maps.model.LatLng;
import com.locweather.R;
import com.locweather.database.WeatherData;
import com.locweather.database.WeatherDatabase;
import com.locweather.maps_activity.MapsActivity;
import com.locweather.maps_activity.RecyclerItemClickListener;
import com.locweather.model.Main;
import com.locweather.model.Notice;
import com.locweather.model.Wind;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.locweather.maps_activity.MapsActivity.currentLocation;

@Entity
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.EmployeeViewHolder> {

    private static Wind wind;
    private static ArrayList<Notice> dataList;
    private static Main main;
    private static Date currentTime = Calendar.getInstance().getTime();
    public static String date;
    private Context mContext;
    public static WeatherData weatherData;

    private RecyclerItemClickListener recyclerItemClickListener;
    public NoticeAdapter(ArrayList<Notice> dataList, Main main, Wind wind, RecyclerItemClickListener recyclerItemClickListener,Context context) {
        NoticeAdapter.dataList = dataList;
        NoticeAdapter.main = main;
        NoticeAdapter.wind = wind;
        this.recyclerItemClickListener = recyclerItemClickListener;
        this.mContext=context;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_view_row, parent, false);
        return new EmployeeViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        setDate(currentTime.toString().substring(0,currentTime.toString().length()-18));
        if(getAddressMap()!=null){holder.txtNoticeAddress.setText("Loc: "+getAddressMap());}else{holder.txtNoticeAddress.setText("Loc: Unknown location");}
        holder.imageIcon.setImageURI(Uri.parse("android.resource://com.locweather/drawable/i"+dataList.get(position).getIcon()));
        holder.txtNoticeWind.setText("Wind: "+roundUp(+wind.getSpeed())+"m/s, "+arrow());
        holder.txtNoticeTempMain.setText(roundUp(+main.getTemp())+"°C");
        holder.txtNoticeWeather.setText(dataList.get(position).getWeather()+" : "+dataList.get(position).getInfo());
        holder.txtNoticeTemp.setText("Feels: "+roundUp(+main.getFeelsLike())+"°C ");
        holder.txtNoticeTime.setText(date);
        holder.txtNoticeHumidity.setText("Humidity: "+main.getHumidity()+"%");
        holder.txtNoticePressure.setText("Pressure: "+main.getPressure()+"hPa");
        holder.itemView.setOnClickListener(v -> {
            recyclerItemClickListener.onItemClick(dataList.get(position));
            saveNoticeList(mContext,dataList);
        });
    }

    private static String getAddressMap() {
        return MapsActivity.addressMap;
    }

    private static void setDate(String date) {
        NoticeAdapter.date = date;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    private static LatLng getloc(){
        return currentLocation;
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageIcon;
        TextView txtNoticeWeather, txtNoticeTempMain,txtNoticeTemp, txtNoticeHumidity,txtNoticeAddress,txtNoticePressure,txtNoticeWind,txtNoticeTime;

        EmployeeViewHolder(View itemView) {
            super(itemView);
            imageIcon=itemView.findViewById(R.id.image_icon);
            txtNoticeTime= itemView.findViewById(R.id.txt_time);
            txtNoticeWind= itemView.findViewById(R.id.txt_notice_wind);
            txtNoticeAddress=  itemView.findViewById(R.id.txt_notice_title);
            txtNoticeWeather =  itemView.findViewById(R.id.txt_notice_weather);
            txtNoticeTemp =  itemView.findViewById(R.id.txt_notice_temp);
            txtNoticeHumidity =  itemView.findViewById(R.id.txt_notice_humidity);
            txtNoticePressure =  itemView.findViewById(R.id.txt_notice_pressure);
            txtNoticeTempMain =  itemView.findViewById(R.id.txt_notice_temp_main);
        }
    }
    public static void saveNoticeList(Context context, List<Notice> noticeList) {
        if (context != null && noticeList != null) {
            weatherData= new WeatherData (getAddressMap(),wind.getSpeed(),wind.getDeg(),dataList.get(0).getIcon(),dataList.get(0).getInfo(),dataList.get(0).getWeather(),main.getTemp(),main.getFeelsLike(),main.getHumidity(),main.getPressure(),date,getloc().latitude,getloc().longitude);
            WeatherDatabase.getInstance(context)
                    .weatherDao()
                    .save(weatherData);
        }
    }

    private BigDecimal roundUp(double value){
        return new BigDecimal(""+value).setScale(1, BigDecimal.ROUND_HALF_UP);
    }
    private String arrow(){
        try {
            int st = wind.getDeg();
            int switchy = 0;
            if (st == 0) switchy = 0;
            else if (st > 0 && st < 90) switchy = 4;
            else if (st > 90 && st < 180) switchy = 5;
            else if (st > 180 && st < 270) switchy = 6;
            else if (st > 270 && st < 360) switchy = 7;
            else if (st == 90) switchy = 1;
            else if (st == 180) switchy = 2;
            else if (st == 270) switchy = 3;
            else if (st == 360) switchy = 0;
            switch (switchy) {
                case 4: return "NE⇗";
                case 5: return "SE⇘";
                case 6: return "SW⇙";
                case 7: return "NW⇖";
                case 0: return "N⇑";
                case 1: return "E⇒";
                case 2: return "S⇓";
                case 3: return "W⇐";
                default: return "Error";
            }
        }
        catch (Exception e){return "";}

    }

}