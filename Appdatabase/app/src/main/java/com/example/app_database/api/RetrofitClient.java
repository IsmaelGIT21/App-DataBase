package com.example.app_database.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://viacep.com.br";
    private static Retrofit retrofit = null;
    public static ViaCepService getViaCepService(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
          return retrofit.create(ViaCepService.class);
    }
}
