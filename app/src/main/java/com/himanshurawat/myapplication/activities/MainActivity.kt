package com.himanshurawat.myapplication.activities

import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.offline.DownloadService
import com.google.android.exoplayer2.offline.ProgressiveDownloadAction
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.himanshurawat.myapplication.R
import com.himanshurawat.myapplication.services.VideoDownloadService
import com.himanshurawat.myapplication.utils.DownloadUtils


class MainActivity : AppCompatActivity() {

    private var playerView: PlayerView? = null
    private var player: SimpleExoPlayer? = null

    private val uri = Uri.parse("https://socialcops.com/images/old/spec/home/header-img-background_video-1920-480.mp4")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Finding PlayerView (Although Not Required in Kotlin)
        playerView = findViewById(R.id.activity_main_player_view)


    }

    override fun onStart() {
        super.onStart()

        progressiveDownload()
        initializePlayer()

    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }


    private fun initializePlayer(){
        if(player == null){

            player = ExoPlayerFactory.newSimpleInstance(this,DefaultTrackSelector())
            playerView?.player = player

            val defaultDataSourceFactory = DefaultDataSourceFactory(this,
                    Util.getUserAgent(this,"my_application"))

            val cacheDataSourceFactory = CacheDataSourceFactory(DownloadUtils.getCache(this),
                    defaultDataSourceFactory)

            val extractorMediaSource = ExtractorMediaSource.
                    Factory(cacheDataSourceFactory).
                    createMediaSource(uri)
            player?.prepare(extractorMediaSource)
            player?.playWhenReady = true
        }

    }

    private fun releasePlayer(){
        playerView?.player = null
        player = null
    }


    private fun progressiveDownload(){

        val progressiveDownloadAction = ProgressiveDownloadAction(uri,
                false,null,null )

        DownloadService.startWithAction(this@MainActivity,
                VideoDownloadService::class.java,
                progressiveDownloadAction,
                false)
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        if(newConfig != null){
            if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }

    }
}
