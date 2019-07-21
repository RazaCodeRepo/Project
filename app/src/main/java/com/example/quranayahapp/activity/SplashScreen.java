package com.example.quranayahapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.quranayahapp.R;
import com.example.quranayahapp.utils.TsvUtils;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new LoadData(SplashScreen.this).execute();

    }



    private class LoadData extends AsyncTask<String, Void, String>{

        Context context;

        public LoadData(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            TsvUtils.extractDataFromTsvFile(context);

            return "Data Loaded";
        }

        @Override
        protected void onPostExecute(String s) {
            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            //finish();
        }
    }


}
