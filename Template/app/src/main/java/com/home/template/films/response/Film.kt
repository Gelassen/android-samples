package com.home.template.films.response

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.home.template.utils.Transformable


@Entity
data class Film
constructor(
        var voteCount: Int? = 0,
        @PrimaryKey
        var id: Int?,
        var video: Boolean? = false,
        var voteAverage: Double? = 0.0,
        var title: String? = "",
        var popularity: Double? = 0.0,
        var posterPath: String? = "",
        var originalLanguage: String? = "",
        var originalTitle: String? = "",
        var genreIds: List<Int>? = listOf(),
        var backdropPath: String? = "",
        var adult: Boolean? = false,
        var overview: String? = "",
        var releaseDate: String? = "",
        var fav: Boolean = false
)
    : Transformable<Film> {

    override fun transform(): Film {
        return this
    }
}