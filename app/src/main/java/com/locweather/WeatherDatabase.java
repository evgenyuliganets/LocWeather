package com.locweather;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
//*
@Database(entities = {WeatherData.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    private static WeatherDatabase INSTANCE;
    public abstract WeatherDataDao weatherDao();
    private static final Object sLock = new Object();
    public static WeatherDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        WeatherDatabase.class, "Posts.db")
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }
    }
}
