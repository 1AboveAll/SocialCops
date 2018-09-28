package com.himanshurawat.myapplication.activities

import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
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
import com.himanshurawat.myapplication.pojo.Model
import com.himanshurawat.myapplication.services.VideoDownloadService
import com.himanshurawat.myapplication.utils.Constants
import com.himanshurawat.myapplication.utils.DownloadUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var playerView: PlayerView? = null
    private var player: SimpleExoPlayer? = null

    private lateinit var uri: Uri

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Finding PlayerView (Although Not Required in Kotlin)
        playerView = findViewById(R.id.activity_main_player_view)

        val model: Model = intent.getSerializableExtra(Constants.INTENT_KEY) as Model
        uri = Uri.parse(model.videoUrl)

        val orientation = resources.configuration.orientation

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            titleTextView = findViewById(R.id.activity_main_video_title_text_view)
            descriptionTextView = findViewById(R.id.activity_main_video_description_text_view)

            titleTextView.text = model.videoTitle
            descriptionTextView.text = model.videoDescription
        }








    }

    override fun onStart() {
        super.onStart()

        progressiveDownload(uri)
        initializePlayer(uri)

    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }


    private fun initializePlayer(source: Uri){
        if(player == null){

            player = ExoPlayerFactory.newSimpleInstance(this,DefaultTrackSelector())
            playerView?.player = player

            val defaultDataSourceFactory = DefaultDataSourceFactory(this,
                    Util.getUserAgent(this,"my_application"))

            val cacheDataSourceFactory = CacheDataSourceFactory(DownloadUtils.getCache(this),
                    defaultDataSourceFactory)

            val extractorMediaSource = ExtractorMediaSource.
                    Factory(cacheDataSourceFactory).
                    createMediaSource(source)
            player?.prepare(extractorMediaSource)
            player?.playWhenReady = true
        }

    }

    private fun releasePlayer(){
        playerView?.player = null
        player = null
    }


    private fun progressiveDownload(source: Uri){

        val progressiveDownloadAction = ProgressiveDownloadAction(source,
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
