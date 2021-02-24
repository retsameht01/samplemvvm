package com.gm.interviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gm.interviewapp.data.ItunesRepository
import com.gm.interviewapp.utils.RetrofitBuilder
import com.gm.interviewapp.utils.Status
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
        viewModel.getSongs().observe(this, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    val adapter = SongAdapter(resource.data!!)
                    song_list.layoutManager = LinearLayoutManager(this)
                    song_list.adapter = adapter
                    lookup_progress.visibility = View.GONE
                }

                Status.LOADING -> {
                    lookup_progress.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    lookup_progress.visibility = View.GONE
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun lookUpArtist(view: View) {
        viewModel.lookupSongsForArtist(artist_text_input.text.toString())
    }
}