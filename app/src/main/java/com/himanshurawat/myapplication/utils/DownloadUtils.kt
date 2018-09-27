package com.himanshurawat.myapplication.utils

import android.content.Context
import com.google.android.exoplayer2.offline.DownloadManager
import com.google.android.exoplayer2.offline.ProgressiveDownloadAction
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import java.io.File

class DownloadUtils {


    companion object {

        private var cache: Cache? = null
        private var downloadManager: DownloadManager? = null

        @Synchronized public fun getCache(context: Context): Cache{
            if(cache == null){
                //NoOpCacheEvitor won't clear the Cache
                val cacheDirectory = File(context.getExternalFilesDir(null),"downloads")
                cache = SimpleCache(cacheDirectory,NoOpCacheEvictor())
            }
            return cache as Cache
        }

        @Synchronized public fun getDownloadManager(context: Context): DownloadManager {
            if(downloadManager == null){
                val actionFile = File(context.getExternalCacheDir(),"actions")
                downloadManager = DownloadManager(getCache(context),
                        DefaultDataSourceFactory(context,
                                Util.getUserAgent(context,"my_application")),
                        actionFile,ProgressiveDownloadAction.DESERIALIZER)
            }

            return downloadManager as DownloadManager
        }
    }
}