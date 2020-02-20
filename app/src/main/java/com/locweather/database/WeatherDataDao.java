package com.locweather.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeatherDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<WeatherData> weathers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(WeatherData weather);

    @Update
    void update(WeatherData weather);

    @Delete
    void delete(WeatherData weather);

    @Query("SELECT * FROM WeatherData")
    LiveData<List<WeatherData>> findAll();

}
