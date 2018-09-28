package com.himanshurawat.myapplication.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himanshurawat.myapplication.R
import com.himanshurawat.myapplication.adapter.ListAdapter
import com.himanshurawat.myapplication.pojo.Model
import com.himanshurawat.myapplication.utils.Constants

class ListActivity : AppCompatActivity(), ListAdapter.OnRowItemClicked {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAdapter
    private lateinit var items: List<Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        title = getString(R.string.library)

        items = dummyData()

        recyclerView = findViewById(R.id.activity_list_recycler_view)
        adapter = ListAdapter(this,arrayListOf(),this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        adapter.addList(items)


    }


    override fun onRowItemClicked(position: Int) {
        val detailsIntent = Intent(this,MainActivity::class.java)
        detailsIntent.putExtra(Constants.INTENT_KEY,items[position])
        startActivity(detailsIntent)
    }

    private fun dummyData():List<Model>{
        val list:MutableList<Model> = ArrayList()
        val noThumbnail = "https://images.pexels.com/photos/87452/flowers-background-butterflies-beautiful-87452.jpeg"
        val videoUrl = "https://socialcops.com/images/old/spec/home/header-img-background_video-1920-480.mp4"
        val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
        val title = "Lorem Ipsum"


        for(count in 1..20){
            val model = Model(noThumbnail,videoUrl, "$title $count",description)
            list.add(model)
        }

        return list
    }

}