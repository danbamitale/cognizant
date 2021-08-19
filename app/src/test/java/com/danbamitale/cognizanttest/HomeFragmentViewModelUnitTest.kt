package com.danbamitale.cognizanttest

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danbamitale.cognizanttest.models.Album
import com.danbamitale.cognizanttest.repositories.AlbumDao
import com.danbamitale.cognizanttest.repositories.AppDataBase
import com.danbamitale.cognizanttest.viewmodels.HomeFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentViewModelUnitTest {


    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutinesTestRule.dispatcher)


    private lateinit var db: AppDataBase
    private lateinit var albumDao: AlbumDao
    private val albumService = MockAlbumService()


    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDataBase::class.java).build()
        albumDao = db.albumDao()


    }

    @ExperimentalCoroutinesApi
    @Test
    fun testObserveLiveAlbum(): Unit =  coroutinesTestRule.runBlocking {
        val viewModel = HomeFragmentViewModel(albumDao, albumService, testScope)
        viewModel.refresh()
        val list = viewModel.liveAlbum.getOrAwaitValue(10)
        assertNotNull(list)
    }


    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        db.close()
        testScope.cleanupTestCoroutines()
    }

}