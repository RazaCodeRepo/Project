package com.example.quranayahapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quranayahapp.R;
import com.example.quranayahapp.database.AppDatabase;
import com.example.quranayahapp.database.Ayah;
import com.example.quranayahapp.utils.TsvUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class AyahTextsFragment extends Fragment {

    private static final String TAG = AyahTextsFragment.class.getSimpleName();

    public static final String RANDON_AYAH_INDEX_BUNDLE_KEY = "random_verse_index";

    @BindView(R.id.english_name)
    TextView mTextViewEnglishName;

    @BindView(R.id.arabic_name)
    TextView mTextViewArabicName;

    @BindView(R.id.english_translation_name)
    TextView mTextViewTranslatedName;

    @BindView(R.id.surah_number)
    TextView mTextViewSurahNumber;

    @BindView(R.id.verse_number)
    TextView mTextViewVerseNumber;

    @BindView(R.id.arabic_verse)
    TextView mTextViewArabicVerse;

    @BindView(R.id.english_translation_verse)
    TextView mTextViewEnglishVerse;

    private AppDatabase mDb;


    public AyahTextsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ayah_text, container, false);

        ButterKnife.bind(this, rootView);

        mDb = AppDatabase.getInstance(getActivity().getApplicationContext());

        Bundle bundle = getArguments();
        if(bundle != null){
            int randomAyahIndex = bundle.getInt(RANDON_AYAH_INDEX_BUNDLE_KEY);
            Log.v(TAG, String.valueOf(randomAyahIndex));

            List<Ayah> ayahList = mDb.ayahDao().loadAllAyahs();
            Ayah selectedAyah = ayahList.get(randomAyahIndex);

            mTextViewEnglishName.setText(selectedAyah.getSurah_name_english());
            mTextViewArabicName.setText(selectedAyah.getSurah_name_arabic());
            mTextViewTranslatedName.setText(selectedAyah.getName_english_meaning());
            mTextViewSurahNumber.setText(selectedAyah.getChapter_number());
            mTextViewVerseNumber.setText(selectedAyah.getAyat_number());
            mTextViewArabicVerse.setText(selectedAyah.getArabic_ayah());
            mTextViewEnglishVerse.setText(selectedAyah.getAyah_english_translation());
        }

        return rootView;
    }

}
