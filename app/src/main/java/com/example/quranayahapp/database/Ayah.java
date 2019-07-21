package com.example.quranayahapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Ayah {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String ayat_number;
    private String surah_name_english;
    private String surah_name_arabic;
    private String chapter_number;
    private String name_english_meaning;
    private String arabic_ayah;
    private String url_audio_assudais;
    private String url_audio_alafasy;
    private String url_audio_almuaiqly;
    private String ayah_english_translation;
    private String downloaded_ayah_path;

    @Ignore
    public Ayah(String ayat_number, String surah_name_english, String surah_name_arabic, String chapter_number, String name_english_meaning,
                     String arabic_ayah, String url_audio_assudais, String url_audio_alafasy,
                     String url_audio_almuaiqly, String ayah_english_translation, String downloaded_ayah_path) {

        this.ayat_number = ayat_number;
        this.surah_name_english = surah_name_english;
        this.surah_name_arabic = surah_name_arabic;
        this.chapter_number = chapter_number;
        this.name_english_meaning = name_english_meaning;
        this.arabic_ayah = arabic_ayah;
        this.url_audio_assudais = url_audio_assudais;
        this.url_audio_alafasy = url_audio_alafasy;
        this.url_audio_almuaiqly = url_audio_almuaiqly;
        this.ayah_english_translation = ayah_english_translation;
        this.downloaded_ayah_path = downloaded_ayah_path;
    }

    public Ayah(int id, String ayat_number, String surah_name_english, String surah_name_arabic, String chapter_number, String name_english_meaning,
                     String arabic_ayah, String url_audio_assudais, String url_audio_alafasy,
                     String url_audio_almuaiqly, String ayah_english_translation, String downloaded_ayah_path) {

        this.id = id;
        this.ayat_number = ayat_number;
        this.surah_name_english = surah_name_english;
        this.surah_name_arabic = surah_name_arabic;
        this.chapter_number = chapter_number;
        this.name_english_meaning = name_english_meaning;
        this.arabic_ayah = arabic_ayah;
        this.url_audio_assudais = url_audio_assudais;
        this.url_audio_alafasy = url_audio_alafasy;
        this.url_audio_almuaiqly = url_audio_almuaiqly;
        this.ayah_english_translation = ayah_english_translation;
        this.downloaded_ayah_path = downloaded_ayah_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAyat_number() {
        return ayat_number;
    }

    public void setAyat_number(String ayat_number) {
        this.ayat_number = ayat_number;
    }

    public String getSurah_name_english() {
        return surah_name_english;
    }

    public void setSurah_name_english(String surah_name_english) {
        this.surah_name_english = surah_name_english;
    }

    public String getSurah_name_arabic() {
        return surah_name_arabic;
    }

    public void setSurah_name_arabic(String surah_name_arabic) {
        this.surah_name_arabic = surah_name_arabic;
    }

    public String getChapter_number() {
        return chapter_number;
    }

    public void setChapter_number(String chapter_number) {
        this.chapter_number = chapter_number;
    }

    public String getName_english_meaning() {
        return name_english_meaning;
    }

    public void setName_english_meaning(String name_english_meaning) {
        this.name_english_meaning = name_english_meaning;
    }

    public String getArabic_ayah() {
        return arabic_ayah;
    }

    public void setArabic_ayah(String arabic_ayah) {
        this.arabic_ayah = arabic_ayah;
    }

    public String getUrl_audio_assudais() {
        return url_audio_assudais;
    }

    public void setUrl_audio_assudais(String url_audio_assudais) {
        this.url_audio_assudais = url_audio_assudais;
    }

    public String getUrl_audio_alafasy() {
        return url_audio_alafasy;
    }

    public void setUrl_audio_alafasy(String url_audio_alafasy) {
        this.url_audio_alafasy = url_audio_alafasy;
    }

    public String getUrl_audio_almuaiqly() {
        return url_audio_almuaiqly;
    }

    public void setUrl_audio_almuaiqly(String url_audio_almuaiqly) {
        this.url_audio_almuaiqly = url_audio_almuaiqly;
    }

    public String getAyah_english_translation() {
        return ayah_english_translation;
    }

    public void setAyah_english_translation(String ayah_english_translation) {
        this.ayah_english_translation = ayah_english_translation;
    }

    public String getDownloaded_ayah_path() {
        return downloaded_ayah_path;
    }

    public void setDownloaded_ayah_path(String downloaded_ayah_path) {
        this.downloaded_ayah_path = downloaded_ayah_path;
    }

}
