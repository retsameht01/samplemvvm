package com.gm.interviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gm.interviewapp.data.ItunesRepository
import com.gm.interviewapp.utils.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = ItunesRepository(RetrofitBuilder.apiClient)
        viewModel = MainViewModel(repository)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getSongs().observe(this, Observer { songs ->
            val adapter = SongAdapter(songs)
            song_list.adapter = adapter
            song_list.layoutManager = LinearLayoutManager(this)
            lookup_progress.visibility = View.GONE
        })
    }

    fun lookUpArtist(view: View) {
        //Do lookup
        viewModel.lookupSongsForArtist(artist_text_input.text.toString())
        lookup_progress.visibility = View.VISIBLE
    }
}