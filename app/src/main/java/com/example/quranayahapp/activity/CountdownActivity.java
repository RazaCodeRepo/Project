package com.example.quranayahapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.example.quranayahapp.R;
import com.example.quranayahapp.database.AppDatabase;
import com.example.quranayahapp.fragments.AyahTextsFragment;
import com.example.quranayahapp.database.Ayah;
import com.example.quranayahapp.utils.TsvUtils;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountdownActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = CountdownActivity.class.getSimpleName();

    public static final String EXTRA_KEY_RANDOM_NUMBER = "random_number";

    public static final String EXTRA_KEY_PREV_SURAH_NUMBER = "previous_surah_number";
    public static final String EXTRA_KEY_PREV_AYAH_NUMBER = "previous_ayah_number";


    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;
    private int nextRandomNumber;

    @BindView(R.id.textView_timerview_time)
    TextView textViewShowTime;

    @BindView(R.id.prev_surah_number)
    TextView textViewPreviousSurahNumber;

    @BindView(R.id.prev_verse_number)
    TextView getTextViewPreviousAyahNumber;

    @BindView(R.id.next_surah_number)
    TextView textViewNextSurahNumber;

    @BindView(R.id.next_verse_number)
    TextView getTextViewNextAyahNumber;

    private int time;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        ButterKnife.bind(this);

        mDb = AppDatabase.getInstance(getApplicationContext());

        FragmentManager fragmentManager = getSupportFragmentManager();

        AyahTextsFragment ayahTextsFragment = new AyahTextsFragment();

        int randomNumber = getIntent().getExtras().getInt(EXTRA_KEY_RANDOM_NUMBER);

        String previous_surah_number = getIntent().getExtras().getString(EXTRA_KEY_PREV_SURAH_NUMBER);
        String previous_ayah_number = getIntent().getExtras().getString(EXTRA_KEY_PREV_AYAH_NUMBER);

        Bundle bundle = new Bundle();
        bundle.putInt(AyahTextsFragment.RANDON_AYAH_INDEX_BUNDLE_KEY, randomNumber);

        Random random = new Random();
        nextRandomNumber = random.nextInt(790);

        List<Ayah> mList = mDb.ayahDao().loadAllAyahs();
        Ayah nextAyah = mList.get(nextRandomNumber);

        ayahTextsFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.ayah_text_container, ayahTextsFragment)
                .commit();

        textViewPreviousSurahNumber.setText(previous_surah_number);
        getTextViewPreviousAyahNumber.setText(previous_ayah_number);

        textViewNextSurahNumber.setText(nextAyah.getChapter_number());
        getTextViewNextAyahNumber.setText(nextAyah.getAyat_number());

        setTimer();
        startTimer(this);

    }

    private void setTimer(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        time = Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_time_key),
                getString(R.string.pref_time_default)));

        totalTimeCountInMilliseconds =  time * 1000;
        // mProgressBar1.setMax( time * 1000);
    }

    private void startTimer(Context context) {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                //mProgressBar1.setProgress((int) (leftTimeInMilliseconds));

                textViewShowTime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
            }
            @Override
            public void onFinish() {
                textViewShowTime.setText("00:00");
                textViewShowTime.setVisibility(View.VISIBLE);

                SharedPreferences sharedPref = getSharedPreferences(
                        "com.example.quranayahapp.activity.countdownactivity", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("NEXT_NUMBER", nextRandomNumber);
                editor.commit();


                Intent intent = new Intent(CountdownActivity.this, MainActivity.class);
                intent.putExtra("id","count");
                intent.putExtra(MainActivity.EXTRA_KEY_RANDOM_NUMBER, nextRandomNumber);
                context.startActivity(intent);
            }
        }.start();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_time_key))){
            time = Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_time_key),
                    getString(R.string.pref_time_default)));

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }
}
