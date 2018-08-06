package com.Aryan.echo.adapters

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.Aryan.echo.R
import com.Aryan.echo.Songs
import com.Aryan.echo.activities.MainActivity
import com.Aryan.echo.fragments.MainScreenFragment
import com.Aryan.echo.fragments.SongPlayingFragment

class MainScreenAdapter(_songDetails: ArrayList<Songs>, _context: Context)
    : RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>() {
    private var selectedPos = RecyclerView.NO_POSITION
    var songDetails: ArrayList<Songs>? = null
    var mContext: Context? = null

    init {
        this.songDetails = _songDetails
        this.mContext = _context
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val songObject = songDetails?.get(position)
        holder.trackTitle?.text = songObject?.songTitle
        holder.trackArtist?.text = songObject?.artist
        holder.contentHolder?.setOnClickListener({
            val songPlayingFragment = SongPlayingFragment()
            var args = Bundle()
            args.putString("songArtist",songObject?.artist)
            args.putString("path",songObject?.songData)
            args.putString("songTitle",songObject?.songTitle)
            args.putInt("songId",songObject?.songID?.toInt() as Int)
            args.putInt("songPosition", position)
            args.putParcelableArrayList("songData", songDetails)
            songPlayingFragment.arguments = args
            SongPlayingFragment.Statified.back = "MainScreen"
            if(SongPlayingFragment.Statified.mediaPlayer != null && SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean){
                SongPlayingFragment.Statified.mediaPlayer?.pause()
                SongPlayingFragment.Statified.mediaPlayer?.release()
            }
            (mContext as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragment, songPlayingFragment, "SongPlayingFragment")
                    .addToBackStack("SongPlayingFragment")
                    .commit()
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.row_custom_mainscreen_adapter, parent, false)

        return MyViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        if (songDetails == null) {
            return 0
        }else {
            return (songDetails as ArrayList<Songs>).size
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var trackTitle: TextView? = null
        var trackArtist: TextView? = null
        var contentHolder: RelativeLayout? = null

        init {
            trackTitle = view.findViewById(R.id.trackTitle) as TextView
            trackArtist = view.findViewById(R.id.trackArtist) as TextView
            contentHolder = view.findViewById(R.id.contentRow) as RelativeLayout
        }
    }

}