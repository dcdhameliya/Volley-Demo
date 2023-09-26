package com.example.volleydemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class StringAdapter(
    var context: Context,
    var list: ArrayList<String>,
    listener: OnItemClickListener
) :
    RecyclerView.Adapter<StringAdapter.VideoHolder>() {


    private var listener: OnItemClickListener = listener

    private val paddingPixel: Int

    init {
        val density = context.resources.displayMetrics.density
        paddingPixel = (10 * density).toInt()
    }

    interface OnItemClickListener {
        fun onItemClick(name: String)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VideoHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list, viewGroup, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {

        holder.tvName.text = list[position]

        holder.itemView.setOnClickListener {
            listener.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
    }
}