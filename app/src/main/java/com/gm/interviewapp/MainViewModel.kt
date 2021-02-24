package com.gm.interviewapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gm.interviewapp.data.ItunesRepository
import com.gm.interviewapp.data.Song
import kotlinx.coroutines.launch

class MainViewModel(private val intunesRepo: ItunesRepository): ViewModel() {

    private val songsData: MutableLiveData<List<Song>> = MutableLiveData()

    fun getSongs(): LiveData<List<Song>> {
        return songsData
    }

    fun lookupSongsForArtist(artist: String) {
        viewModelScope.launch {
            val songs =  intunesRepo.getSongsByArtist(artist)
            songsData.postValue(songs)
        }
    }

}