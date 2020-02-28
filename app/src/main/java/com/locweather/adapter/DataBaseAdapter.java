package com.locweather.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.locweather.R;
import com.locweather.database.WeatherData;
import com.locweather.maps_activity.RecyclerItemClickListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataBaseAdapter extends RecyclerView.Adapter<DataBaseAdapter.DataViewHolder> {

    private List<WeatherData> data;
    private LayoutInflater layoutInflater;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;
    private RecyclerItemClickListener recyclerItemClickListener;
    public DataBaseAdapter(Context context, OnDeleteButtonClickListener listener,RecyclerItemClickListener recyclerItemClickListener) {
        this.data = new ArrayList<>();
        this.onDeleteButtonClickListener = listener;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.database_view, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void setData(List<WeatherData> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClicked(WeatherData weather);
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        Button deleteButton;
        ImageView imageIcon;
        TextView txtNoticeWeather, txtNoticeTempMain,txtNoticeTemp, txtNoticeHumidity,txtNoticeAddress,txtNoticePressure,txtNoticeWind,txtNoticeTime;

        DataViewHolder(View itemView) {
            super(itemView);
            imageIcon=itemView.findViewById(R.id.image_db_icon);
            txtNoticeTime= itemView.findViewById(R.id.txt_db_time);
            txtNoticeWind= itemView.findViewById(R.id.txt_db_wind);
            txtNoticeAddress=  itemView.findViewById(R.id.txt_db_title);
            txtNoticeWeather =  itemView.findViewById(R.id.txt_db_weather);
            txtNoticeTemp =  itemView.findViewById(R.id.txt_db_temp);
            txtNoticeHumidity =  itemView.findViewById(R.id.txt_db_humidity);
            txtNoticePressure =  itemView.findViewById(R.id.txt_db_pressure);
            txtNoticeTempMain =  itemView.findViewById(R.id.txt_db_temp_main);
            deleteButton=itemView.findViewById(R.id.delete_button);

        }

        @SuppressLint("SetTextI18n")
        void bind(final WeatherData weather) {
            if (weather != null) {
                if(weather.getAddress()!=null){txtNoticeAddress.setText("Loc: "+weather.getAddress());}else{txtNoticeAddress.setText("Loc: Unknown location");}
                imageIcon.setImageURI(Uri.parse("android.resource://com.locweather/drawable/i"+weather.getDatalistIcon()));
                if(weather.getWindDegree()!=null)txtNoticeWind.setText("Wind: "+roundUp(weather.getWindSpeed())+"m/s, "+arrow(weather.getWindDegree())); else {txtNoticeWind.setText("Wind: "+roundUp(weather.getWindSpeed())+"m/s");}
                txtNoticeTempMain.setText(roundUp(+weather.getMainTemp())+"°C");
                txtNoticeWeather.setText(weather.getDatalistWeather()+" : "+weather.getDatalistInfo());
                txtNoticeTemp.setText("Feels: "+roundUp(+weather.getMainFeel())+"°C ");
                txtNoticeTime.setText(weather.getTime());
                txtNoticeHumidity.setText("Humidity: "+weather.getMainHumidity()+"%");
                txtNoticePressure.setText("Pressure: "+weather.getMainPressure()+"hPa");
                deleteButton.setOnClickListener(v -> {
                    if (onDeleteButtonClickListener != null)
                        recyclerItemClickListener.onItemClick();
                        Objects.requireNonNull(onDeleteButtonClickListener).onDeleteButtonClicked(weather);
                });
                itemView.setOnClickListener(v -> {
                    if (onDeleteButtonClickListener != null)
                        recyclerItemClickListener.onItemClick();
                    Objects.requireNonNull(onDeleteButtonClickListener).onDeleteButtonClicked(weather);
                });

            }
        }
        private BigDecimal roundUp(double value){
            return new BigDecimal(""+value).setScale(1, BigDecimal.ROUND_HALF_UP);
        }
        private String arrow(int deg){
            try {
                int switchy = 0;
                if (deg > 0 && deg < 90) switchy = 4;
                else if (deg > 90 && deg < 180) switchy = 5;
                else if (deg > 180 && deg < 270) switchy = 6;
                else if (deg > 270 && deg < 360) switchy = 7;
                else if (deg == 90) switchy = 1;
                else if (deg == 180) switchy = 2;
                else if (deg == 270) switchy = 3;
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
            catch (Exception e){return " ";}

        }

    }
}
