package com.second_layer_catalog.data.di

import com.second_layer_catalog.data.RepositoryImpl
import com.second_layer_catalog.data.network.NetworkApi
import com.second_layer_catalog.domain.SecondLayerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
interface SecondLayerDI {
    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): SecondLayerRepository

    companion object{
        @Provides
        fun provideApi(retrofit: Retrofit): NetworkApi{
            return retrofit.create(NetworkApi::class.java)
        }
    }
}