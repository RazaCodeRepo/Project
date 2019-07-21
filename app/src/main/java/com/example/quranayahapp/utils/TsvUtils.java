package com.example.quranayahapp.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.quranayahapp.R;
import com.example.quranayahapp.database.AppDatabase;
import com.example.quranayahapp.database.Ayah;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TsvUtils {

    private static final String TAG = TsvUtils.class.getSimpleName();

    private static List<Ayah> mAyahList;

    private static AppDatabase mDb;



//    public static void extractDataFromTsvFile(Context context, AppDatabase mDb){
//
//       // mDb = AppDatabase.getInstance(context);
//
//        mAyahList = new ArrayList<Ayah>();
//
//        InputStream inputStream = context.getResources().openRawResource(R.raw.quran_english_v2);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        mAyahList = new ArrayList<Ayah>();
//        try {
//            bufferedReader.readLine();
//            String dataRow = bufferedReader.readLine();
//
//            while(dataRow != null){
//                String[] entrySeparatedAtLine = dataRow.split("\t+");
//                String[] surahNames = seprateEnglishArabicSurahNames(entrySeparatedAtLine[1]);
//                Ayah tempAyah = new Ayah(entrySeparatedAtLine[0], surahNames[0], surahNames[1], entrySeparatedAtLine[2],
//                        entrySeparatedAtLine[3],entrySeparatedAtLine[4],entrySeparatedAtLine[5],
//                        entrySeparatedAtLine[6],entrySeparatedAtLine[7],entrySeparatedAtLine[8], null);
//
//                Log.d(TAG, "Inserting in database");
//                mDb.ayahDao().insertAyah(tempAyah);
////                AppExecutors.getInstance().diskIO().execute(new Runnable() {
////                    @Override
////                    public void run() {
////                        // COMPLETED (3) Move the remaining logic inside the run method
////                        mDb.ayahDao().insertAyah(tempAyah);
////
////                    }
////                });
//                Log.d(TAG, "Inserted in database");
//
//                mAyahList.add(tempAyah);
//                dataRow = bufferedReader.readLine();
//
//            }
//            bufferedReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public static void extractDataFromTsvFile(Context context){

         mDb = AppDatabase.getInstance(context);
//         mDb.ayahDao().nukeDatabase();

        mAyahList = new ArrayList<Ayah>();

        InputStream inputStream = context.getResources().openRawResource(R.raw.quran_english_v2);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        mAyahList = new ArrayList<Ayah>();
        try {
            bufferedReader.readLine();
            String dataRow = bufferedReader.readLine();

            while(dataRow != null){
                String[] entrySeparatedAtLine = dataRow.split("\t+");
                String[] surahNames = seprateEnglishArabicSurahNames(entrySeparatedAtLine[1]);
                Ayah tempAyah = new Ayah(entrySeparatedAtLine[0], surahNames[0], surahNames[1], entrySeparatedAtLine[2],
                        entrySeparatedAtLine[3],entrySeparatedAtLine[4],entrySeparatedAtLine[5],
                        entrySeparatedAtLine[6],entrySeparatedAtLine[7],entrySeparatedAtLine[8], null);

                Log.d(TAG, "Inserting in database");
                mDb.ayahDao().insertAyah(tempAyah);
//                AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        // COMPLETED (3) Move the remaining logic inside the run method
//                        mDb.ayahDao().insertAyah(tempAyah);
//
//                    }
//                });
                Log.d(TAG, "Inserted in database");

                mAyahList.add(tempAyah);
                dataRow = bufferedReader.readLine();

            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

      public static List<Ayah> getmAyahList() {
        return mDb.ayahDao().loadAllAyahs();
    }

//    private static String[] seprateEnglishArabicSurahNames(String surahName){
//        String[] names = new String[2];
//        if(surahName.contains("--")){
//            names = surahName.split("--");
//        } else {
//            int lastIndex = surahName.lastIndexOf("-");
//            names[0] = surahName.substring(0, lastIndex);
//            names[1] = surahName.substring(lastIndex, surahName.length());
//        }
//        return names;
//    }

    private static String[] seprateEnglishArabicSurahNames(String name) {
        String[] sepNames = null;
        if(name.contains("—")) {
            sepNames = name.split("—");
        } else if(name.contains("--")) {
            sepNames = name.split("--");
        } else {
            sepNames = new String[2];
            int lastIndex = name.lastIndexOf("-");
            sepNames[0] = name.substring(0, lastIndex);
            sepNames[1] = name.substring(lastIndex, name.length());
        }
        return sepNames;
    }

//    public static void updateList(Ayah ayah, int index){
//        mAyahList.add(index, ayah);
//    }
//
//    private void checkDirectory(){
//        String path = Environment.getExternalStorageDirectory().toString()+"/QuranAyahApp";
//        Log.d("Files", "Path: " + path);
//        File directory = new File(path);
//        File[] files = directory.listFiles();
//        Log.d("Files", "Size: "+ files.length);
//        for (int i = 0; i < files.length; i++)
//        {
//            Log.d("Files", "FileName:" + files[i].getName());
//        }
//    }
}

