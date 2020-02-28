package com.locweather.di;

import android.app.Application;

import com.locweather.maps_activity.GetNoticeIntractorImpl;
import com.locweather.maps_activity.MapsActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;


@Component(modules = {WebServiceModule.class, GetNoticeIntractorImpl.AnotherModule.class})
@Singleton
public interface MapsComponent  {
    @Component.Factory
    interface Factory {
        MapsComponent create(@BindsInstance Application application);
    }

    void bindMainActivity(MapsActivity activity);


}
