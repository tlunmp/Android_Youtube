package com.ted_uy.final4020.Adaptors

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.ted_uy.final4020.Models.StoreDataModel
import com.ted_uy.final4020.PlayerActivity
import com.ted_uy.final4020.R
import kotlinx.android.synthetic.main.video_row.view.*


class MainAdapter(val feed : List<StoreDataModel>) : RecyclerView.Adapter<CustomViewHolder>() {

    private val FADE_DURATION = 1000

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val video = feed[position]
        holder.videoTitle.text = video.videoTitles

        Picasso.get().load(video.urlImage).into(holder.thumbnailsImageView)

        holder.view.setOnClickListener {
            val intent = Intent(holder.view.context, PlayerActivity::class.java)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(holder.view.context as Activity,holder.thumbnailsImageView,
                ViewCompat.getTransitionName(holder.thumbnailsImageView).toString()
            )

            intent.putExtra("VideoId", video.videoid)
            intent.putExtra("VideoDescription", video.description)
            intent.putExtra("VideoTitle", video.videoTitles)
            intent.putExtra("URLImage", video.urlImage)
            holder.view.context.startActivity(intent, option.toBundle())
        }
        setScaleAnimation(holder.itemView)

    }

    private fun setScaleAnimation(view: View) {
        val anim =
            ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.setDuration(FADE_DURATION.toLong())
        view.startAnimation(anim)
    }

    override fun getItemCount(): Int {
        return feed.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflator = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflator.inflate(R.layout.video_row,parent, false)
        return CustomViewHolder(cellForRow)
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){
    val thumbnailsImageView: ImageView = view.thumbnailView
    val videoTitle: TextView = view.videoTitles
}