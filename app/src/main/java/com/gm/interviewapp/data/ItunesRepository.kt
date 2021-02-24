package com.gm.interviewapp.data

class ItunesRepository(private val itunesClient: ItunesApiClient) {

    suspend fun getSongsByArtist(artist: String): List<Song>? {
        val response = itunesClient.getSongsForArtist(artist)
        return response.results
    }
}