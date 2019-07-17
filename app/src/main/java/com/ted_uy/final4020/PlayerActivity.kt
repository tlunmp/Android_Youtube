package com.ted_uy.final4020

import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.ted_uy.final4020.Models.DataModel
import com.ted_uy.final4020.Models.StoreNewData
import kotlinx.android.synthetic.main.activity_player.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PlayerActivity : YouTubeBaseActivity() {

    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)


        //pass data from other
        val dataModel = DataModel()
        dataModel.setVideoId(getIntent().extras!!.getString("VideoId"))
        dataModel.setVideoDescription(getIntent().extras!!.getString("VideoDescription"))
        dataModel.setVideoTitle(getIntent().extras!!.getString("VideoTitle"))
        dataModel.setURLImage(getIntent().extras!!.getString("URLImage"))


        videoTitles.setText(dataModel.getVideoTitle())
        description.setText(dataModel.getVideoDescription())

        initUI(dataModel.getVideoId())
        youtube_view.initialize(dataModel.getAPI(), youtubePlayerInit)

        val storeFaveList: StoreNewData = dataModel.getAsStoredData()

        val ifFavorited = MainActivity.dbHelper.checkIfFavorited(dataModel.getVideoId())

        Log.e("msg","${ifFavorited}")

        //check if it is favorited if not dont disable the button
        if (ifFavorited == true){
            favButton.isEnabled = false
        } else {
            favButton.isEnabled = true
        }

        //favbutton add it
        favButton.setOnClickListener {
           MainActivity.dbHelper.addFavList(storeFaveList)
            favButton.isEnabled = false
        }


    }

    //initialize the youtube player
    private fun initUI(videoId: String) {
        println(videoId)
        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                youtubePlayer: YouTubePlayer?,
                p2: Boolean
            ) {

                youtubePlayer?.cueVideo(videoId)
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                println("wrong")
            }

        }
    }
}