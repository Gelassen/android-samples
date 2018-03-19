package com.home.kotlinkoan

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var listener: ClickListener? = null

    var data = mutableListOf<String>()

    fun setListener(listener: ClickListener) {
        this.listener = listener
    }

    fun updateDataSource(data: List<String>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.setText(data.get(position))
        holder.itemView.setOnClickListener{
            listener?.onClick(data.get(position))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface ClickListener {
        fun onClick(data: String?)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val item: TextView

        init {
            item = itemView.findViewById(R.id.text)
        }
    }

}