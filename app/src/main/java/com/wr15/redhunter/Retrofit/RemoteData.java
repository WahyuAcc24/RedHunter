package com.wr15.redhunter.Retrofit;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.wr15.redhunter.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RemoteData {
    private Context context;

    public static final String BASE_URL = "http://t-hisyam.net/mila/rh/";
    private static Retrofit retrofit = null;

    public RemoteData(Context contextIn){
        context = contextIn;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public interface StoreDataService {
        @GET("list_brg.php/")
        Call<DataBarang> getDataBarang();
    }

    public void getDataBarang(){

        retrofit.create(StoreDataService.class).getDataBarang()
                .enqueue(new Callback<DataBarang>() {

                    @Override
                    public void onResponse(@NotNull Call<DataBarang> call,
                                           @NotNull Response<DataBarang> response) {

                       if (response.isSuccessful()) {
                           List <String> str = new ArrayList<>();
                           for(DataBarangData s : response.body().getDataBarang()){
                               str.add(s.getNamaBarang());
                           }

                           AutoCompleteTextView storeTV = ((Activity) context).findViewById(R.id.edtnamabrg);

                           ArrayAdapter<String> adapteo = new ArrayAdapter<>(
                                   context,
                                   android.R.layout.simple_dropdown_item_1line,
                                   str.toArray(new String[0])
                           );
                           storeTV.setAdapter(adapteo);
                       }
                    }
                    @Override
                    public void onFailure(Call<DataBarang> call, Throwable t) {
                        Log.e("Async Data RemoteData",
                                "error in getting remote data");
                    }
                });
    }

}
