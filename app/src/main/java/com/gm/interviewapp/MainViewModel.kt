package com.gm.interviewapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gm.interviewapp.data.ItunesRepository
import com.gm.interviewapp.data.Song
import com.gm.interviewapp.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val intunesRepo: ItunesRepository): ViewModel() {

    private val songsData: MutableLiveData<Resource<List<Song>>> = MutableLiveData()

    fun getSongs(): LiveData<Resource<List<Song>>> {
        return songsData
    }

    fun lookupSongsForArtist(artist: String) {
        songsData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val songs =  intunesRepo.getSongsByArtist(artist)
            try {
                songsData.postValue(Resource.success(songs))
            } catch (exception: Exception) {
                songsData.postValue(Resource.error(null, "$exception.message"))
            }
        }
    }

}