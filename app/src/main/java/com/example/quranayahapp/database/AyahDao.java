package com.example.quranayahapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AyahDao {

    @Query("SELECT * FROM ayah")
    List<Ayah> loadAllAyahs();

    @Insert
    void insertAyah(Ayah ayah);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAyahEntry(Ayah ayah);

    @Query("DELETE FROM ayah")
    public void nukeDatabase();

    @Query("SELECT * FROM ayah WHERE id = :id")
    Ayah loadAyahById(int id);
}
