package com.himanshurawat.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himanshurawat.myapplication.R
import com.himanshurawat.myapplication.pojo.Model


class ListAdapter(val context: Context, var itemList:List<Model>, val listener: OnRowItemClicked) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_item,parent,false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val model = itemList[pos]

        holder.titleTextView.text = model.videoTitle
        holder.descriptionTextView.text = model.videoDescription
        //Picasso.get().load(model.thumbnailUrl).
        Glide.with(context).load(R.drawable.thumbnail).into(holder.thumbnailImageView)
        holder.bind(pos,listener)

    }

    fun addList(list: List<Model>){
        this.itemList = list
        notifyDataSetChanged()
    }


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView = itemView.findViewById<TextView>(R.id.row_item_title_text_view)
        val thumbnailImageView = itemView.findViewById<ImageView>(R.id.row_item_image_view)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.row_item_description_text_view)

        fun bind(pos:Int, listener:OnRowItemClicked){
            itemView.setOnClickListener {
                listener.onRowItemClicked(pos)
            }
        }

    }

    interface OnRowItemClicked{
        fun onRowItemClicked(position:Int)
    }
}