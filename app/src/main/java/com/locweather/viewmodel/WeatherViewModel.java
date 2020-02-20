package com.locweather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.locweather.database.WeatherData;
import com.locweather.database.WeatherDataDao;
import com.locweather.database.WeatherDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherViewModel extends AndroidViewModel {

    private WeatherDataDao weatherDataDao;
    private ExecutorService executorService;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherDataDao = WeatherDatabase.getInstance(application).weatherDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<WeatherData>> getAllPosts() {
        return weatherDataDao.findAll();
    }

    public void saveWeather(WeatherData weather) {
        executorService.execute(() -> weatherDataDao.save(weather));
    }

    public void deleteWeather(WeatherData weather) {
        executorService.execute(() -> weatherDataDao.delete(weather));
    }
}