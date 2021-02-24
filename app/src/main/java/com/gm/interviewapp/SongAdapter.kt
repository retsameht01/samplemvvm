package com.gm.interviewapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gm.interviewapp.data.Song

class SongAdapter(private val songs: List<Song> ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_layout, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SongViewHolder).bind(songs[position])
    }

    override fun getItemCount(): Int {
        return songs.size
    }


    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val artistTv: TextView = itemView.findViewById(R.id.artist)
        private val trackTv: TextView = itemView.findViewById(R.id.track_name)
        private val releaseTv: TextView = itemView.findViewById(R.id.release_date)
        private val genreTv: TextView = itemView.findViewById(R.id.genre)
        private val albumArt: ImageView = itemView.findViewById(R.id.album_art)

        fun bind(song: Song) {
            artistTv.text = song.artistName
            trackTv.text = song.trackName
            releaseTv.text = song.releaseDate
            genreTv.text = song.primaryGenreName

            Glide.with(itemView.context).load(song.artworkUrl60)
                .into(albumArt)
        }
    }
}