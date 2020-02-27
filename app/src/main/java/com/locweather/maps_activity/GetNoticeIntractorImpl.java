package com.locweather.maps_activity;

import android.annotation.SuppressLint;

import com.google.android.gms.maps.model.LatLng;
import com.locweather.main_interface.GetNoticeDataService;
import com.locweather.network.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.locweather.maps_activity.MapsActivity.currentLocation;

public class GetNoticeIntractorImpl implements MainContract.GetNoticeIntractor {
    private LatLng getloc(){
        return currentLocation;
    }
    @SuppressLint("CheckResult")
    @Override
    public void getNoticeArrayList(final OnFinishedListener onFinishedListener) {

        /** Create handle for the RetrofitInstance interface*/
        GetNoticeDataService service = RetrofitInstance.getRetrofitInstance().create(GetNoticeDataService.class);
        /** Using RxJava Observable response to handle retrofit api*/
        if(currentLocation!=null) {
            service.getNoticeData(getloc().latitude, getloc().longitude)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(items -> onFinishedListener.onFinished(items.getNoticeArrayList(), items.getMain(), items.getWind()), onFinishedListener::onFailure);

        }
    }

}
