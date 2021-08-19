package com.danbamitale.cognizanttest

import android.content.Context
import androidx.room.Room
import com.danbamitale.cognizanttest.di.DatabaseModule
import com.danbamitale.cognizanttest.repositories.AlbumDao
import com.danbamitale.cognizanttest.repositories.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object IntegrationTestDatabaseModule {

    @Provides
    @Singleton
    fun provideTestAppDatabase(@ApplicationContext context: Context): AppDataBase {
       return Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
    }

    @Provides
    @Singleton
    fun provideTestAlbumDao(dataBase: AppDataBase): AlbumDao {
        return dataBase.albumDao()
    }
}