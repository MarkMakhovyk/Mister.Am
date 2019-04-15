package com.markmahovyk.misteram.data.newtwork;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static NetworkService networkService;

    private static final String BASE_URL = "https://test.mister.am";
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        networkService = retrofit.create(NetworkService.class);
    }

    public static NetworkService getApi() {
        return networkService;
    }
}
