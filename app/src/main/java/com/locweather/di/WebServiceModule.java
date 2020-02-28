package com.locweather.di;

import com.locweather.main_interface.GetNoticeDataService;
import com.locweather.network.RetrofitInstance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class WebServiceModule {
    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return RetrofitInstance.getRetrofitInstance();
    }

    @Singleton
    @Provides
    GetNoticeDataService provideGetNoticeDataService(Retrofit retrofit) {
        return retrofit.create(GetNoticeDataService.class);
    }
}
