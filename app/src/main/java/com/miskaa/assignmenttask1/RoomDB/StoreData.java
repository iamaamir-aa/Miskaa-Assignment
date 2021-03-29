package com.miskaa.assignmenttask1.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "region_data",indices = @Index(value = {"name"},unique = true))
public class StoreData {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "capital")
    public String capital;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] flag_image;

    public byte[] getFlag_image() {
        return flag_image;
    }

    public void setFlag_image(byte[] flag_image) {
        this.flag_image = flag_image;
    }

    @ColumnInfo(name = "region")
    public String region;

    @ColumnInfo(name = "subregion")
    public String subregion;

    @ColumnInfo(name = "population")
    public String population;

    @ColumnInfo(name = "border")
    public String border;

    @ColumnInfo(name = "language")
    public String language;



    public StoreData(String name, String capital, byte[] flag_image, String region, String subregion, String population, String border, String language) {

        this.name = name;
        this.capital = capital;
        this.flag_image = flag_image;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.border = border;
        this.language = language;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "StoreData{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", capital='" + capital + '\'' +
                ", region='" + region + '\'' +
                ", subregion='" + subregion + '\'' +
                ", population='" + population + '\'' +
                ", border='" + border + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}