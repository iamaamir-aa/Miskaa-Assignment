package com.miskaa.assignmenttask1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.caverock.androidsvg.SVG;
import com.google.gson.Gson;
import com.miskaa.assignmenttask1.RoomDB.StoreData;
import com.miskaa.assignmenttask1.RoomDB.myRoomDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String regions_api = "https://restcountries.eu/rest/v2/region/asia";
    myRoomDatabase mydatabase;
    List<StoreData> all_data;
    CustomAdapter customAdapter;
    RecyclerView myRecyclerView;

    public void updateRecyclerView(){
        try {
            all_data = mydatabase.regionDao().getAllData();
            if (all_data != null) {
                customAdapter=new CustomAdapter(all_data);
                myRecyclerView.setAdapter(customAdapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        customAdapter.notifyDataSetChanged();
    }
    public InputStream getInputStream(String url){
        try{URL url_region_api = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) url_region_api.openConnection();
        urlConnection.connect();
        InputStream inputStream = urlConnection.getInputStream();
        return inputStream;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //<--------------Setting Up the Database--------->>
    private void setUpDb() {
        mydatabase = Room.databaseBuilder(getApplicationContext(), myRoomDatabase.class, "RegionDataBase")
                .build();
    }

//<------------Method to convert Svg File to the Byte array--------->>


    @RequiresApi(api = Build.VERSION_CODES.P)
    public byte[] svgToByte(String url_svg) {
        try {
            InputStream inputStream_image = getInputStream(url_svg);
            if(inputStream_image!=null) {
                SVG svg = SVG.getFromInputStream(inputStream_image);
                Picture picture = svg.renderToPicture();
                Bitmap bitmap = Bitmap.createBitmap(picture);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image_byte_array = stream.toByteArray();
                return image_byte_array;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something Failed", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public class DownloadTask extends Thread{
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void run() {
            super.run();
            try {
                updateRecyclerView();
                InputStream inputStream=getInputStream(regions_api);
                if(inputStream!=null) {
                    InputStreamReader streamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(streamReader);
                    StringBuilder asia_content = new StringBuilder();
                    String current = "";
                    while ((current = bufferedReader.readLine()) != null) {
                        asia_content.append(current);
                    }

                    JSONArray region_Json_array = new JSONArray(asia_content.toString());
                    JSONObject jsonObject = null;
                    for (int i = 0; i < region_Json_array.length(); i++) {
                        jsonObject = region_Json_array.getJSONObject(i);
                        mydatabase.regionDao().insertData(new StoreData(
                                jsonObject.getString("name")
                                , jsonObject.getString("capital")
                                , svgToByte(jsonObject.getString("flag"))
                                , jsonObject.getString("region")
                                , jsonObject.getString("subregion")
                                , jsonObject.getString("population")
                                , jsonObject.getString("borders")
                                , jsonObject.getString("languages")));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateRecyclerView();
        }
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpDb();
        myRecyclerView = findViewById(R.id.recyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DownloadTask downloadTask=new DownloadTask();
        downloadTask.start();
    }
}
