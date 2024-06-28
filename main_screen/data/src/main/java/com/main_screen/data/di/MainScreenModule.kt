package com.main_screen.data.di

import com.main_screen.data.RemoteRepositoryImpl
import com.main_screen.data.ServiceApi
import com.main_screen.domain.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
interface MainScreenModule {
    companion object{
        @Provides
        fun providesServiceApi(retrofit: Retrofit): ServiceApi {
            return retrofit.create(ServiceApi::class.java)
        }
    }
    @Binds
    fun bindRepository(remoteRepositoryImpl: RemoteRepositoryImpl): RemoteRepository
}