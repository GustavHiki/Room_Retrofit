package com.example.room_retrofit.di

import android.content.Context
import androidx.room.Room
import com.example.room_retrofit.room.PikabuPostDao
import com.example.room_retrofit.room.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun providePostDb(@ApplicationContext context: Context): PostDatabase {
        return Room
            .databaseBuilder(
                context,
                PostDatabase::class.java,
                PostDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePostDAO(postDatabase: PostDatabase): PikabuPostDao{
        return postDatabase.postDao()
    }

}