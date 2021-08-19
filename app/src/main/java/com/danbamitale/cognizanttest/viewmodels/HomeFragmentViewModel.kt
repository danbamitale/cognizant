package com.danbamitale.cognizanttest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.danbamitale.cognizanttest.models.Album
import com.danbamitale.cognizanttest.repositories.AlbumDao
import com.danbamitale.cognizanttest.services.AlbumService
import com.danbamitale.cognizanttest.utils.getViewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(val albumDao: AlbumDao, val albumService: AlbumService, val coroutineScopeProvider: CoroutineScope?): ViewModel() {
    private val pageSize = 50
    private val coroutineScope = getViewModelScope(coroutineScopeProvider)

    private val _loading = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Throwable>()
    val onError: LiveData<Throwable> = _error




    private val boundaryCallback =  object : PagedList.BoundaryCallback<Album>() {
        override fun onZeroItemsLoaded() {
            getAlbum()
        }
    }


    val liveAlbum = LivePagedListBuilder(albumDao.getAll(), pageSize)
        .setBoundaryCallback(boundaryCallback)
        .setInitialLoadKey(1)
        .build()

    private fun getAlbum() {
        _loading.postValue(true)
        coroutineScope.launch {
            kotlin.runCatching {
                albumDao.save(albumService.getAlbums())
                println("get album called")
                _loading.postValue(false)
            }.onFailure {
                _loading.postValue(false)
                _error.postValue(it)
            }
        }
    }

    fun refresh() {
        getAlbum()
    }
}