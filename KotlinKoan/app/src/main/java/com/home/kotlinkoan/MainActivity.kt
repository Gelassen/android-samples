package com.home.kotlinkoan

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity(), CustomAdapter.ClickListener {

    var rv: RecyclerView? = null
    var adapter: CustomAdapter? = CustomAdapter()

    var data: kotlin.collections.ArrayList<String>

    init {
        data = ArrayList()
        data.add("hello")
        data.add("to")
        data.add("this")
        data.add("world")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.list)
        rv?.layoutManager = LinearLayoutManager(this)
        rv?.adapter = adapter
        adapter!!.setListener(this)
        adapter!!.updateDataSource(data)
    }

    override fun onClick(data: String?) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra("key", data)
        startActivity(intent)
    }
}
