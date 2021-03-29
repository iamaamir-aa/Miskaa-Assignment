package com.miskaa.assignmenttask1.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RegionDao {



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertData(StoreData storeData);

    @Query("SELECT  * FROM region_data ORDER BY name ASC")
    List<StoreData> getAllData();


}

