package com.example.networking;

import com.example.networking.api.ToDoApi;
import com.example.networking.plugin.NetworkingHost;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppNetworkingModule {

    @Singleton
    @Provides
    public Retrofit provideRetrofit(@NetworkingHost String host) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(host)
                .build();
    }

    @Singleton
    @Provides
    public ToDoApi provideToDoApi(Retrofit retrofit) {
        return retrofit.create(ToDoApi.class);
    }

}
