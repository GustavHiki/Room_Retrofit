package com.example.room_retrofit.di

import com.example.room_retrofit.network.IPikabuServices
import com.example.room_retrofit.network.NetworkMapper
import com.example.room_retrofit.room.CacheMapper
import com.example.room_retrofit.room.PikabuPostDao
import com.example.room_retrofit.veiwModel.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePostsRepository(
        postDao: PikabuPostDao,
        pikabuServices: IPikabuServices,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): PostsRepository {
        return PostsRepository(postDao, pikabuServices, cacheMapper, networkMapper)
    }
}