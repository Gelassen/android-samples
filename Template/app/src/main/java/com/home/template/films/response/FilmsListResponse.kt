package ru.surfstudio.standard.interactor.films.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.home.template.films.response.Film
import com.home.template.utils.TransformUtil
import com.home.template.utils.Transformable
import io.reactivex.Observable

class FilmsListResponse: Transformable<List<Film>> {

    val defaultSize: Int = 20

    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("results")
    @Expose
    var filmResponses: List<FilmResponse>? = null

    override fun transform(): List<Film> {
        return TransformUtil()
                .transformCollection(filmResponses!!)
    }

}
