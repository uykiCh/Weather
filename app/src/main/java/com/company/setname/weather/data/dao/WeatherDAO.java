package com.company.setname.weather.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.company.setname.weather.data.model.ModelDatabase;
import java.util.List;

@Dao
public interface WeatherDAO {

    @Query("SELECT * FROM weather")
    List<ModelDatabase> getAll();

    @Insert
    void insert(ModelDatabase modelDatabase);

    @Query("DELETE FROM weather WHERE :currentTime - time > 10800000")
    void deleteUseless(long currentTime);

    @Query("DELETE FROM weather")
    void deleteAll();

}
