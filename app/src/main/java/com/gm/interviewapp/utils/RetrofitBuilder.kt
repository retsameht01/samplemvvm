package com.gm.interviewapp.utils

import com.gm.interviewapp.data.ItunesApiClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val ITUNES_ARTIST_API_URL = "https://itunes.apple.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ITUNES_ARTIST_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiClient: ItunesApiClient = getRetrofit().create(ItunesApiClient::class.java)
}