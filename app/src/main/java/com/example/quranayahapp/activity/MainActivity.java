package com.example.quranayahapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.quranayahapp.R;
import com.example.quranayahapp.database.AppDatabase;
import com.example.quranayahapp.fragments.AyahTextsFragment;

import com.example.quranayahapp.utils.DownloadAudio;
import com.example.quranayahapp.utils.TsvUtils;
import com.example.quranayahapp.database.Ayah;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


import static com.example.quranayahapp.utils.ScreenshotUtils.getWaterMarkedImage;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_KEY_RANDOM_NUMBER = "random_number_main";

    private List<Ayah> mAyahList;
    private Ayah currentAyah;

    private boolean play_status = false;
    private boolean automaticPlay;

    private MediaPlayer mMediaPlayer;
    private int randomNumber;

    private String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int EXTERNAL_STORAGE_PERMISSION = 200;
    private boolean permissionForExternalStorage = true;

    @BindView(R.id.play_button)
    ImageButton imageButtonPlay;

    @BindView(R.id.ayah_text_container)
    FrameLayout frameLayoutAyahTextContainer;

    int currentPosition;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mDb = AppDatabase.getInstance(getApplicationContext());

//        TsvUtils.extractDataFromTsvFile(this);

//        SharedPreferences sharedPref = getSharedPreferences(
//                "com.example.quranayahapp.activity.countdownactivity", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        boolean firstRunTest = sharedPref.getBoolean("FIRST_RUN", false);
//        if(!firstRunTest){
//            TsvUtils.extractDataFromTsvFile(this, mDb);
//            editor.putBoolean("FIRSTRUN", true);
//            editor.commit();
//        }
        ButterKnife.bind(this);

        mAyahList = mDb.ayahDao().loadAllAyahs();

        Log.v(TAG, String.valueOf(mAyahList.size()));

        mMediaPlayer = new MediaPlayer();

        ActivityCompat.requestPermissions(this, permissions, EXTERNAL_STORAGE_PERMISSION);

        SharedPreferences sharedPref = getSharedPreferences(
        "com.example.quranayahapp.activity.countdownactivity", Context.MODE_PRIVATE);
        int nextNumber = sharedPref.getInt("NEXT_NUMBER", -1);

        if(nextNumber == -1){
            TsvUtils.extractDataFromTsvFile(this);
            Random random = new Random();
            randomNumber = random.nextInt(6237);
        } else {
            randomNumber = nextNumber;
            sharedPref.edit().putInt("NEXT_NUMBER", -1).commit();
        }




//        if(getIntent().getExtras() == null){
//            Random random = new Random();
//            randomNumber = random.nextInt(790);
//
//        } else {
//            randomNumber = getIntent().getExtras().getInt(MainActivity.EXTRA_KEY_RANDOM_NUMBER);
//            trackingToasts("other bundle");
//        }

        //randomNumber = 70;

        currentAyah = mAyahList.get(randomNumber);

        automaticPlay = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.pref_play_audio_key), getResources()
                .getBoolean(R.bool.pref_play_audio_auto));

        FragmentManager fragmentManager = getSupportFragmentManager();

        AyahTextsFragment ayahTextsFragment = new AyahTextsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(AyahTextsFragment.RANDON_AYAH_INDEX_BUNDLE_KEY, randomNumber);

        ayahTextsFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.ayah_text_container, ayahTextsFragment)
                .commit();



    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean("PREVIOUSLY_STARTED", false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("PREVIOUSLY_STARTED", Boolean.TRUE);
            edit.commit();
            Intent splashScreenIntent = new Intent(this, SplashScreen.class);
            startActivity(splashScreenIntent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayer.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION) {
            permissionForExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionForExternalStorage) finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsActivityIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void onClickPlayButton(View view) {
        Toast.makeText(this, "Play Button", Toast.LENGTH_SHORT).show();

        List<Ayah> list = mDb.ayahDao().loadAllAyahs();
        Ayah current = list.get(randomNumber);
        String path = current.getDownloaded_ayah_path();

        if(current.getDownloaded_ayah_path() == null){
            trackingToasts("Downloading Ayah");
            showDownloadDialogBox();
        } else {
            trackingToasts("Downloaded Ayah");
            playAudio(current.getUrl_audio_alafasy());
        }

    }

    public void onClickShareButton(View view) {
        Toast.makeText(this, "Share Button", Toast.LENGTH_SHORT).show();
        shareIt(frameLayoutAyahTextContainer);
    }

    public void onClickNextButton(View view) {
        Toast.makeText(this, "Next Button", Toast.LENGTH_SHORT).show();
        Intent startCountdownActivity = new Intent(this, CountdownActivity.class);

        Random random = new Random();
        int randomNumber = random.nextInt(790);

        startCountdownActivity.putExtra(CountdownActivity.EXTRA_KEY_PREV_AYAH_NUMBER, currentAyah.getAyat_number());
        startCountdownActivity.putExtra(CountdownActivity.EXTRA_KEY_PREV_SURAH_NUMBER, currentAyah.getChapter_number());

        startCountdownActivity.putExtra(CountdownActivity.EXTRA_KEY_RANDOM_NUMBER, randomNumber);

        startActivity(startCountdownActivity);
    }

    private void shareIt(View view) {

        Bitmap bitmap = getWaterMarkedImage(view);
        try {
            File file = new File(this.getExternalCacheDir(),"quranayahapp.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_play_audio_key))){
            automaticPlay = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.pref_play_audio_key), getResources()
                    .getBoolean(R.bool.pref_play_audio_auto));
        }
    }

    private void trackingToasts(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showDownloadDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.download_dialog_title));
        builder.setMessage(getResources().getString(R.string.download_dialog_message))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.download_dialog_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        trackingToasts("Yes Download");
                        //changePlayButtonDrawable();
                        new DownloadAudio(MainActivity.this, currentAyah.getUrl_audio_alafasy(), randomNumber).execute();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.download_dialog_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        trackingToasts("don't download");
                        playAudio(currentAyah.getUrl_audio_alafasy());
                    }
                }).show();
    }

    private void changePlayButtonDrawable(){
        Drawable pauseButtonDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.white_pause_button, null);
        Drawable playButtonDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.white_play_button, null);

        if(play_status){
            trackingToasts("paused playing");
            imageButtonPlay.setImageDrawable(playButtonDrawable);
            play_status = false;
        } else {
            trackingToasts("started playing");
            imageButtonPlay.setImageDrawable(pauseButtonDrawable);
            play_status = true;
        }
    }

    private void playAudio(String file){
        try{
            mMediaPlayer.setDataSource(file);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void pauseAudio(){
        currentPosition = mMediaPlayer.getCurrentPosition();
        mMediaPlayer.pause();
    }


}
