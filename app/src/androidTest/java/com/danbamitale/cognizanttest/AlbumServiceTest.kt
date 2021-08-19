package com.danbamitale.cognizanttest

import com.danbamitale.cognizanttest.services.AlbumService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class AlbumServiceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var albumService: AlbumService

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testGetAlbums(): Unit = runBlocking{
        val albums = albumService.getAlbums()
        assertNotEquals(0, albums.size)
    }

    @Test
    fun testGetSingleAlbum():Unit = runBlocking {
        val album = albumService.getAlbum(1)
        assertNotNull("should not be null", album)
        assertEquals(1, album.id)
    }
}