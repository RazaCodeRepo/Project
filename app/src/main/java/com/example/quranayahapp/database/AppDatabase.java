package com.example.quranayahapp.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quranayahapp.utils.TsvUtils;

@Database(entities = {Ayah.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "ayahdata";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(final Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
//                        .addCallback(new Callback() {
//                            @Override
//                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                                super.onCreate(db);
//
//                                new Thread(new Runnable(){
//
//                                    @Override
//                                    public void run() {
//                                        TsvUtils.extractDataFromTsvFile(context, getInstance(context));
//                                    }
//                                }).start();
//                            }
//                        })
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract AyahDao ayahDao();
}
