package com.sosure.interviewtest.depencyinj

import com.sosure.interviewtest.BuildConfig
import com.sosure.interviewtest.model.PeopleApi
import com.sosure.interviewtest.model.PeopleService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
class ApiModule {
    private val BASE_URL = BuildConfig.BASE_URL


    @Provides
    fun providePeopleApi(): PeopleApi {
        var logging = HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        var httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.client(httpClient.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PeopleApi::class.java)

    }

    @Provides
    fun providePeopleService(): PeopleService{
        return PeopleService()
    }
}