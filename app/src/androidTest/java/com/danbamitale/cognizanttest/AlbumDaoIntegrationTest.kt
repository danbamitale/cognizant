package com.danbamitale.cognizanttest

import android.content.Context
import androidx.room.Room
import androidx.room.paging.LimitOffsetDataSource
import com.danbamitale.cognizanttest.models.Album
import com.danbamitale.cognizanttest.repositories.AlbumDao
import com.danbamitale.cognizanttest.repositories.AppDataBase
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class AlbumDaoIntegrationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: AppDataBase

    @Inject
    lateinit var albumDao: AlbumDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testIsEmptyDB() {
        val factory = albumDao.getAll()
        assertNotNull(factory)

        val list = (factory.create() as LimitOffsetDataSource).loadRange(0, 1)
        assertEquals(0, list.size)
    }

    @Test
    fun  testAddSingleAlbum(): Unit = runBlocking {
        val album = Album(1, "kpk-001")
        albumDao.save(album)
        assertNotNull(albumDao.get(album.id))
    }

    @Test
    fun  testAddMultipleAlbum(): Unit = runBlocking {
        val albums = listOf(Album(1, "kpk-001"), Album(2, "zpp"))
        albumDao.save(albums)

        val factory = albumDao.getAll()
        assertNotNull(factory)

        val list =  (factory.create() as LimitOffsetDataSource).loadRange(0, 10)
        assertEquals(2, list.size)
    }

    @Test
    fun testResultSortOrder(): Unit = runBlocking {
        val albums = listOf(Album(2, "zpp"), Album(1, "kpk-001"))
        albumDao.save(albums)
        val factory = albumDao.getAll()
        val list =  (factory.create() as LimitOffsetDataSource).loadRange(0, 2)
        assertEquals(albums[1], list.first())
    }


    @After
    fun teardown() {
        db.close()
    }
}