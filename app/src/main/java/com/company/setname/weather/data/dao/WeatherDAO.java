package com.company.setname.weather.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.company.setname.weather.data.model.ModelDatabase;
import java.util.List;

@Dao
public interface WeatherDAO {

    @Query("SELECT * FROM weather WHERE city_code = :city_code")
    List<ModelDatabase> getAll(long city_code);

    @Insert
    void insert(ModelDatabase modelDatabase);

    @Query("DELETE FROM weather WHERE :currentTime - time > 10800000")
    void deleteUseless(long currentTime);

    @Query("DELETE FROM weather")
    void deleteAll();

    @Query("DELETE FROM weather WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT MIN(time) FROM weather")
    long getMinTime();

    @Query("SELECT * FROM weather WHERE time = :time")
    ModelDatabase getRowByTime(long time);

    @Query("SELECT * FROM weather WHERE city_code = :city_code LIMIT 8")
    List<ModelDatabase> getAllWithLimit8(long city_code);

    @Query("SELECT * FROM weather WHERE id = :id")
    ModelDatabase getRowById(long id);

}
