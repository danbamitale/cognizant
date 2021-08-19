package com.danbamitale.cognizanttest.repositories

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danbamitale.cognizanttest.models.Album


@Database(entities = [Album::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}