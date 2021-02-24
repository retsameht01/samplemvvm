package com.gm.interviewapp.data

import retrofit2.http.GET
import retrofit2.http.Path

interface ItunesApiClient {
    @GET("search?term={artist}")
    fun getSongsForArtist(@Path("artist") artist: String): SongsResponse
}