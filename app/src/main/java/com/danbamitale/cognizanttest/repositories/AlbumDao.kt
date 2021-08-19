package com.danbamitale.cognizanttest.repositories

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danbamitale.cognizanttest.models.Album


@Dao
interface AlbumDao {
    @Query("SELECT * FROM ALBUM ORDER BY title ASC")
    fun getAll(): DataSource.Factory<Int, Album>

    @Query("SELECT * FROM ALBUM ORDER BY title ASC")
    fun getList(): List<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(album: Album)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(albums: List<Album>)

    @Query("SELECT * FROM ALBUM WHERE id=:id")
    suspend fun get(id: Long): Album?
}