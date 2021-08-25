package com.mihir.ticktockclone

import android.media.MediaPlayer
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_video.view.*

class VideoAdapter(private var videoList :ArrayList<VideoItem>):
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
        inner class  ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val video: VideoView = itemView.video
            var title: TextView = itemView.video_title
            val desc : TextView = itemView.video_desc
            val progressBar: ProgressBar = itemView.progressBar
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_video,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.video.setVideoPath(videoList[position].videoUrl)
        holder.title.text = videoList[position].title
        holder.desc.text = videoList[position].description

        holder.video.setOnPreparedListener {
            holder.progressBar.visibility = View.GONE
            it.start()
        }

        holder.video.setOnCompletionListener {
            it.start()
        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}