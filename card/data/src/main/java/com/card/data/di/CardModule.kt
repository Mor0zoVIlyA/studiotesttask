package com.card.data.di

import com.card.data.CardRepositoryImpl
import com.card.data.network_model.NetworkApi
import com.card.domain.CardRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
interface CardModule {
    @Binds
    fun bindService(cardRepository: CardRepositoryImpl): CardRepository

    companion object{
        @Provides
        fun provideApi(retrofit: Retrofit): NetworkApi{
            return retrofit.create(NetworkApi::class.java)
        }
    }
}