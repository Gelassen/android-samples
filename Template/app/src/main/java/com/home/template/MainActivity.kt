package com.home.template

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.home.template.films.storage.FilmsRepository
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var filmsRepository : FilmsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
