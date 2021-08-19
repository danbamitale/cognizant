package com.danbamitale.cognizanttest.di

import android.content.Context
import androidx.room.Room
import com.danbamitale.cognizanttest.repositories.AlbumDao
import com.danbamitale.cognizanttest.repositories.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton





@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "app.db").build()
    }

    @Provides
    fun provideAlbumDao(db: AppDataBase): AlbumDao {
        return db.albumDao()
    }
}