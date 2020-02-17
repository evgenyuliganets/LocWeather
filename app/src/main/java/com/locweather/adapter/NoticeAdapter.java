package com.locweather.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;

import com.locweather.R;
import com.locweather.maps_activity.MapsActivity;
import com.locweather.maps_activity.RecyclerItemClickListener;
import com.locweather.model.Main;
import com.locweather.model.Notice;
import com.locweather.model.Wind;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Entity
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.EmployeeViewHolder> {
    private  Wind wind;
    private ArrayList<Notice> dataList;
    private Main main;
    private Date currentTime = Calendar.getInstance().getTime();

    private RecyclerItemClickListener recyclerItemClickListener;
    public NoticeAdapter(ArrayList<Notice> dataList, Main main, Wind wind, RecyclerItemClickListener recyclerItemClickListener) {
        this.dataList = dataList;
        this.main = main;
        this.wind = wind;
        this.recyclerItemClickListener = recyclerItemClickListener;
    }


    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_view_row, parent, false);
        return new EmployeeViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String m;
        if(getAddressMap()!=null){holder.txtNoticeAddress.setText("Loc: "+getAddressMap());}else{holder.txtNoticeAddress.setText("Loc: Unknown location");}
        holder.imageIcon.setImageURI(Uri.parse("android.resource://com.locweather/drawable/i"+dataList.get(position).getIcon()));
        holder.txtNoticeWind.setText("Wind: "+roundUp(+wind.getSpeed(),1)+"m/s, "+arrow());
        holder.txtNoticeTempMain.setText(roundUp(+main.getTemp(),1)+"°C");
        holder.txtNoticeWeather.setText(dataList.get(position).getWeather()+" : "+dataList.get(position).getInfo());
        holder.txtNoticeTemp.setText("Feels: "+roundUp(+main.getFeelsLike(),1)+"°C ");
        holder.txtNoticeTime.setText(currentTime.toString().substring(0,currentTime.toString().length()-18));
        holder.txtNoticeHumidity.setText("Humidity: "+main.getHumidity()+"%");
        holder.txtNoticePressure.setText("Pressure: "+main.getPressure()+"hPa");
        holder.itemView.setOnClickListener(v -> recyclerItemClickListener.onItemClick(dataList.get(position)));
    }

    public String getAddressMap() {
        return MapsActivity.addressMap;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
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
    public BigDecimal roundUp(double value, int digits){
        return new BigDecimal(""+value).setScale(digits, BigDecimal.ROUND_HALF_UP);
    }
    public String arrow(){
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