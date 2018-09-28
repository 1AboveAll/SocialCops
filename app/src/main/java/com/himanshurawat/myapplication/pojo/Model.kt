package com.himanshurawat.myapplication.pojo

import java.io.Serializable

data class Model(val thumbnailUrl: String,
                 val videoUrl: String,
                 val videoTitle: String,
                 val videoDescription: String): Serializable