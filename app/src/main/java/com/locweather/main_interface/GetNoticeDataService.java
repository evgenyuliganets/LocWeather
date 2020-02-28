package com.locweather.main_interface;

import com.locweather.model.NoticeList;

import javax.inject.Singleton;

import dagger.Module;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Module
public interface GetNoticeDataService {
    @Singleton
    @GET("weather?appid=0194877ecdcac230396a119c01d46100")
    Observable<NoticeList> getNoticeData(@Query("lat") double lat , @Query("lon") double lon );
}
