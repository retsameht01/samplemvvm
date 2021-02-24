package com.gm.interviewapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiClient {
    @GET("search?")
    suspend fun getSongsForArtist(@Query("term") artist: String): SongsResponse
}