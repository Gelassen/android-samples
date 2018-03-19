package com.home.kotlinkoan


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class FakeAdapter : RecyclerView.Adapter<FakeAdapter.ViewHolder>() {


    var data: List<String> = ArrayList<String>()

    fun updateDataSource(data: List<String>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item.setText(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val item: TextView

        init {
            item = itemView.findViewById(R.id.text)
        }
    }
}
