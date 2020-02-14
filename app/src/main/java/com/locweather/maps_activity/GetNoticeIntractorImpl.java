package com.locweather.maps_activity;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.locweather.main_interface.GetNoticeDataService;
import com.locweather.model.NoticeList;
import com.locweather.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.locweather.maps_activity.MapsActivity.currentLocation;

public class GetNoticeIntractorImpl implements MainContract.GetNoticeIntractor {
    public LatLng getloc(){
        return currentLocation;
    }
    @Override
    public void getNoticeArrayList(final OnFinishedListener onFinishedListener) {


        /** Create handle for the RetrofitInstance interface*/
        GetNoticeDataService service = RetrofitInstance.getRetrofitInstance().create(GetNoticeDataService.class);

        /** Call the method with parameter in the interface to get the notice data*/
        if(currentLocation!=null) {
            Call<NoticeList> call = service.getNoticeData(currentLocation.latitude, currentLocation.longitude);

            /**Log the URL called*/
            Log.wtf("URL Called", call.request().url() + "");

            call.enqueue(new Callback<NoticeList>() {
                @Override
                public void onResponse(Call<NoticeList> call, Response<NoticeList> response) {
                    onFinishedListener.onFinished(response.body().getNoticeArrayList(), response.body().getMain());

                }

                @Override
                public void onFailure(Call<NoticeList> call, Throwable t) {
                    onFinishedListener.onFailure(t);
                }
            });
        }
    }

}
