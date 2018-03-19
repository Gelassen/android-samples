package com.example.expensetracker

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Gelassen on 28.02.2018.
 */
interface Api {
    @GET('https://www.googleapis.com/drive/v2/files')
    fun getFiles()
}