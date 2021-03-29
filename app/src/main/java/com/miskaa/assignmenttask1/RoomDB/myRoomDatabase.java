package com.miskaa.assignmenttask1.RoomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {StoreData.class}, version = 1,exportSchema = false)
public abstract class myRoomDatabase extends RoomDatabase {

    public abstract RegionDao regionDao();

}

