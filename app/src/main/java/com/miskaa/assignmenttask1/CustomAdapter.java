package com.miskaa.assignmenttask1;


import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.miskaa.assignmenttask1.RoomDB.StoreData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.customViewHolder> {
    private List<StoreData> data;

public String  language(String s){
    try {
        JSONArray jsonArray=new JSONArray(s);
        String language="";
        if(jsonArray.getJSONObject(0).getString("name")!=null){
            language=jsonArray.getJSONObject(0).getString("name");
            for(int i=1;i<jsonArray.length();i++){
                language=language+", "+jsonArray.getJSONObject(i).getString("name");
            }
            return language;
         }

    } catch (JSONException e) {
        e.printStackTrace();
    }
return null;
}

    public String border(String str){
        try {
            JSONArray jsonArray=new JSONArray(str);
            String border="";
            if(jsonArray.getString(0)!=null){
                border=jsonArray.getString(0);
                for(int i=1;i<jsonArray.length();i++){
                    border=border+", "+jsonArray.getString(i);
                }
                return border;
            }else if(jsonArray.getString(0).equals("")){
                return "No Border";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    
    }


    public CustomAdapter(List<StoreData> data){
        this.data=data;
    }

    @NonNull
    @Override
    public customViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //---Inflating the view with our Layout file

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_item_layout,parent,false);
        return new customViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customViewHolder holder, int position) {



        //----Showing user the information about the ASIA region Countries---Setting the View
        holder.name.setText(data.get(position).getName());

        holder.capital.setText(data.get(position).getCapital());

        holder.region.setText(data.get(position).getRegion());

        holder.subregion.setText(data.get(position).getSubregion());

        holder.population.setText(data.get(position).getPopulation());

       holder.borders.setText(border(data.get(position).getBorder()));

        holder.languages.setText(language(data.get(position).getLanguage()));



        //---Setting image from the byte array which have been stores in the database
        //---Using BitmapFactory to Convert the the byte array into Bitmap



        holder.flag_image.setImageBitmap( BitmapFactory.decodeByteArray(data.get(position).flag_image
                ,0,data.get(position).flag_image.length));
    }

    @Override
    public int getItemCount() {

        //---Returning the size of the data size

        return data.size();
    }

    public class customViewHolder extends RecyclerView.ViewHolder{
        ImageView flag_image;
        TextView name,capital,region,subregion,population,languages,borders;
        public customViewHolder(@NonNull View itemView) {
            super(itemView);


            //---Finding view particulars



            flag_image=itemView.findViewById(R.id.imageView);
            name=itemView.findViewById(R.id.name);
            capital=itemView.findViewById(R.id.capital);
            region=itemView.findViewById(R.id.region);
            subregion=itemView.findViewById(R.id.subregion);
            population=itemView.findViewById(R.id.population);
            languages=itemView.findViewById(R.id.languages);
            borders=itemView.findViewById(R.id.borders);


        }
    }
}
