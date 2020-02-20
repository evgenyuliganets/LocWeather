package com.locweather.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;


@Database(entities = {WeatherData.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    public static WeatherDatabase INSTANCE;
    private final static List<WeatherData> POSTS = Arrays.asList(
            new WeatherData("safsad",2.2,3,"fsdfsdf","fsdfdsf","fsdfdsf",4.4,3.5,3,2,"fsdfdsf",1.2,5.3));

    public abstract WeatherDataDao weatherDao();
    private static final Object sLock = new Object();
    public static WeatherDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        WeatherDatabase.class, "Weathers.db")
                        .allowMainThreadQueries()
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                Executors.newSingleThreadExecutor().execute(
                                        () -> getInstance(context).weatherDao().saveAll(POSTS));
                            }
                        })
                        .build();
            }
            return INSTANCE;
        }
    }

}
