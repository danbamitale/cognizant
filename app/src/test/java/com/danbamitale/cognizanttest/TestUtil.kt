package com.danbamitale.cognizanttest

import com.danbamitale.cognizanttest.models.Album
import com.danbamitale.cognizanttest.services.AlbumService


class MockAlbumService : AlbumService {
    override suspend fun getAlbums(): List<Album> = listOf(Album(1, "001"), Album(2, "002"), Album(3, "003"))

    override suspend fun getAlbum(id: Long): Album = Album(1, "001")
}