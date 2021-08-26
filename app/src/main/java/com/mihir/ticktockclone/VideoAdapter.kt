package com.mihir.ticktockclone

import android.media.MediaPlayer
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import android.widget.ViewFlipper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_video.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoAdapter(private var videoList :ArrayList<VideoItem>,private var viewPager2: ViewPager2):
    RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
        inner class  ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            val video: VideoView = itemView.video
            var title: TextView = itemView.video_title
            val desc : TextView = itemView.video_desc
            val username : TextView = itemView.video_user
            val progressBar: ProgressBar = itemView.progressBar
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_video,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        CoroutineScope(Dispatchers.Main).launch {

            holder.video.setVideoPath(videoList[position].videoUrl)
            holder.title.text = videoList[position].title
            holder.desc.text = videoList[position].description
            holder.username.text = videoList[position].username


            holder.video.setOnPreparedListener {
                holder.progressBar.visibility = View.GONE
                //finding if the video is stretched or not
                val videoRatio = it.videoWidth / it.videoHeight.toFloat()
                val screenRatio = holder.video.width / holder.video.height.toFloat()
                val scale: Float = videoRatio / screenRatio
                //fixing if stretched
                if (scale >= 1f) {
                    holder.video.scaleX = scale
                } else {
                    holder.video.scaleY = (1f / scale)
                }
                it.start()

            }

            holder.video.setOnCompletionListener {

                it.start()
                viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)

            }
        }
    }

    override fun getItemCount(): Int {

        return videoList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

}