package com.example.quranayahapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.quranayahapp.database.Ayah;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DownloadAudio extends AsyncTask<Void, Void, String> {

    public static final String TAG = DownloadAudio.class.getSimpleName();

    ProgressDialog mProgressDialog;

    Context context;
    String stringUrl;
    int index;

    public DownloadAudio(Context context,String url, int randomNumber) {
        this.context = context;

        stringUrl=url;

        index = randomNumber;

    }

    protected void onPreExecute() {
        mProgressDialog = ProgressDialog.show(context, "Please wait", "Download …");
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {

            URL url = new URL(stringUrl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            String[] path = url.getPath().split("/");
            int temp = path.length - 1;
            String mp3 = path[temp];
            int lengthOfFile = c.getContentLength();

            String PATH = Environment.getExternalStorageDirectory()+ "/QuranAyahApp/" ;
            Log.v(TAG, "PATH: " + PATH);
            File file = new File(PATH);
            file.mkdirs();

            String fileName = mp3;

            File outputFile = new File(file , fileName);
//            List<Ayah> mArrayList = TsvUtils.getmAyahList();
//            Ayah currentAyah = mArrayList.get(index);
//            currentAyah.setDownloaded_ayah_path(outputFile.toString());
//            TsvUtils.updateList(currentAyah, index);

            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {

                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "done";
    }

    protected void onPostExecute(String result) {
        if (result.equals("done")) {
            mProgressDialog.dismiss();
        }
    }

}
