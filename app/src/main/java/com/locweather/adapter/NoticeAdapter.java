package com.locweather.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.locweather.R;
import com.locweather.maps_activity.RecyclerItemClickListener;
import com.locweather.model.Main;
import com.locweather.model.Notice;

import java.math.BigDecimal;
import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.EmployeeViewHolder> {
    private ArrayList<Notice> dataList;
    private Main main;
    private RecyclerItemClickListener recyclerItemClickListener;
    public NoticeAdapter(ArrayList<Notice> dataList, Main main, RecyclerItemClickListener recyclerItemClickListener) {
        this.dataList = dataList;
        this.main = main;
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
        holder.txtNoticeTempMain.setText(roundUp(+main.getTemp(),1)+"°C");
        holder.txtNoticeWeather.setText(dataList.get(position).getWeather()+" : "+dataList.get(position).getInfo());
        holder.txtNoticeTemp.setText("Feels: "+roundUp(+main.getFeelsLike(),1)+"°C "+"Min: "+roundUp(+main.getTempMin(),1)+"°C "+"Max: "+roundUp(+main.getTempMax(),1)+"°C ");
        holder.txtNoticeHumidity.setText("Humidity: "+main.getHumidity()+"%");
        holder.txtNoticePressure.setText("Pressure: "+main.getPressure()+"hPa");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView txtNoticeWeather, txtNoticeTempMain,txtNoticeTemp, txtNoticeHumidity,txtNoticeWind,txtNoticePressure;

        EmployeeViewHolder(View itemView) {
            super(itemView);
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
}