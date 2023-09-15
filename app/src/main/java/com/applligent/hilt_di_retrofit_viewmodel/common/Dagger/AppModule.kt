package com.applligent.hilt_di_retrofit_viewmodel.common.Dagger

import com.applligent.hilt_di_retrofit_viewmodel.common.Network.ApiService
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.Constants
import com.applligent.hilt_di_retrofit_viewmodel.common.Utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(/*Constants.BaseUrl*/BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit) : ApiService =
        retrofit.create(ApiService::class.java)
}