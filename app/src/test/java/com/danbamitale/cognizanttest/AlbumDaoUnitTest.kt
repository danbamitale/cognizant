package com.danbamitale.cognizanttest

import androidx.room.Room
import androidx.room.paging.LimitOffsetDataSource
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danbamitale.cognizanttest.models.Album
import com.danbamitale.cognizanttest.repositories.AlbumDao
import com.danbamitale.cognizanttest.repositories.AppDataBase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AlbumDaoUnitTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var db: AppDataBase
    private lateinit var albumDao: AlbumDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder( ApplicationProvider.getApplicationContext(), AppDataBase::class.java)
            .build()

        albumDao = db.albumDao()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun  testAddSingleAlbum(): Unit = mainCoroutineRule.runBlocking  {
        val album = Album(1, "kpk-001")
        albumDao.save(album)
        assertNotNull(albumDao.get(album.id))
    }


    @ExperimentalCoroutinesApi
    @Test
    fun  testAddMultipleAlbum(): Unit = mainCoroutineRule.runBlocking {
        val albums = listOf(Album(1, "kpk-001"), Album(2, "zpp"))
        albumDao.save(albums)

        val factory = albumDao.getAll()
        assertNotNull(factory)

        val list =  (factory.create() as LimitOffsetDataSource).loadRange(0, 2)
        assertEquals(2, list.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testResultSortOrder(): Unit =  mainCoroutineRule.runBlocking {
        val albums = listOf(Album(2, "zpp"), Album(1, "kpk-001"))
        albumDao.save(albums)
        val factory = albumDao.getAll()
        val list =  (factory.create() as LimitOffsetDataSource).loadRange(0, 2)
        assertEquals(albums[1], list.first())
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        db.close()
    }
}