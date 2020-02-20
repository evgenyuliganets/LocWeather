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

import com.locweather.R;
import com.locweather.database.WeatherData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DataBaseAdapter extends RecyclerView.Adapter<DataBaseAdapter.DataViewHolder> {

    private List<WeatherData> data;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;
    public DataBaseAdapter(Context context, OnDeleteButtonClickListener listener) {
        this.data = new ArrayList<>();
        this.context = context;
        this.onDeleteButtonClickListener = listener;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        }

        @SuppressLint("SetTextI18n")
        void bind(final WeatherData weather) {
            if (weather != null) {
                if(weather.getAddress()!=null){txtNoticeAddress.setText("Loc: "+weather.getAddress());}else{txtNoticeAddress.setText("Loc: Unknown location");}
                imageIcon.setImageURI(Uri.parse("android.resource://com.locweather/drawable/i"+weather.getDatalistIcon()));
                txtNoticeWind.setText("Wind: "+roundUp(+weather.getWindSpeed())+"m/s, "+arrow(weather.getWindDegree()));
                txtNoticeTempMain.setText(roundUp(+weather.getMainTemp())+"°C");
                txtNoticeWeather.setText(weather.getDatalistWeather()+" : "+weather.getDatalistInfo());
                txtNoticeTemp.setText("Feels: "+roundUp(+weather.getMainFeel())+"°C ");
                txtNoticeTime.setText(weather.getTime());
                txtNoticeHumidity.setText("Humidity: "+weather.getMainHumidity()+"%");
                txtNoticePressure.setText("Pressure: "+weather.getMainPressure()+"hPa");
                itemView.setOnClickListener(v -> {
                    if (onDeleteButtonClickListener != null)
                        onDeleteButtonClickListener.onDeleteButtonClicked(weather);
                });

            }
        }
        private BigDecimal roundUp(double value){
            return new BigDecimal(""+value).setScale(1, BigDecimal.ROUND_HALF_UP);
        }
        private String arrow(int deg){
            try {
                int st = deg;
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
}
