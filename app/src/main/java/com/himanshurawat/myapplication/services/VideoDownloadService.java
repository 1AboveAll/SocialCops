package com.himanshurawat.myapplication.services;

import android.app.Notification;

import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.scheduler.Scheduler;
import com.google.android.exoplayer2.ui.DownloadNotificationUtil;
import com.himanshurawat.myapplication.R;
import com.himanshurawat.myapplication.utils.Constants;
import com.himanshurawat.myapplication.utils.DownloadUtils;

import androidx.annotation.Nullable;

public class VideoDownloadService extends DownloadService {


    public VideoDownloadService() {
        super(2,
                DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
                Constants.CHANNEL_ID,
                R.string.video_download);
    }

    @Override
    protected DownloadManager getDownloadManager() {
        return DownloadUtils.Companion.getDownloadManager(this);
    }

    @Nullable
    @Override
    protected Scheduler getScheduler() {
        return null;
    }

    @Override
    protected Notification getForegroundNotification(DownloadManager.TaskState[] taskStates) {
        return DownloadNotificationUtil.buildProgressNotification(this,
                R.drawable.exo_notification_small_icon,
                Constants.CHANNEL_ID,
                null,
                null,
                taskStates);
    }
}
