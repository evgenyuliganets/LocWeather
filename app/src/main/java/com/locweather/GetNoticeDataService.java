package com.locweather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetNoticeDataService {


    @GET("weather?appid=0194877ecdcac230396a119c01d46100")
    Call<NoticeList> getNoticeData(@Query("lat") double lat , @Query("lon") double lon );
}
