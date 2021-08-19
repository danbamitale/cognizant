package com.danbamitale.cognizanttest.services

import com.danbamitale.cognizanttest.models.Album
import retrofit2.http.GET
import retrofit2.http.Path

const val baseUrl = "https://jsonplaceholder.typicode.com"
interface AlbumService {
    @GET("/albums")
    suspend fun getAlbums(): List<Album>

    @GET("/albums/{id}")
    suspend fun getAlbum(@Path("id") id: Long): Album
}