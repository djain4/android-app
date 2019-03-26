package com.example.myapplication

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.recycler_item.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private var listOfVideos: MutableList<String>

    constructor(listOfVideos: MutableList<String>) {
        this.listOfVideos = listOfVideos;
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG,"check size:"+listOfVideos.size.toString())
        return listOfVideos.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.textView.setText(listOfVideos.get(pos))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView = itemView.textView
    }

    private fun setData() {

    }

    companion object {
        private val TAG = RecyclerAdapter::class.qualifiedName
    }
}