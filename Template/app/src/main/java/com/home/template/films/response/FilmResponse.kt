package ru.surfstudio.standard.interactor.films.network.response

import com.google.gson.annotations.SerializedName
import com.home.template.films.storage.model.Film
import com.home.template.utils.Transformable

class FilmResponse
constructor(
        @SerializedName("vote_count") var voteCount: Int? = null,
        @SerializedName("id") var id: Int? = null,
        @SerializedName("video") var video: Boolean? = null,
        @SerializedName("vote_average") var voteAverage: Double? = null,
        @SerializedName("title") var title: String? = null,
        @SerializedName("popularity") var popularity: Double? = null,
        @SerializedName("poster_path") var posterPath: String? = null,
        @SerializedName("original_language") var originalLanguage: String? = null,
        @SerializedName("original_title") var originalTitle: String? = null,
        @SerializedName("genre_ids") var genreIds: List<Int>? = null,
        @SerializedName("backdrop_path") var backdropPath: String? = null,
        @SerializedName("adult") var adult: Boolean? = null,
        @SerializedName("overview") var overview: String? = null,
        @SerializedName("release_date") var releaseDate: String? = null,
        var fav: Boolean = false

) : Transformable<Film?> {
    override fun transform(): Film? {
        return null
//        return Film(
//                voteCount,
//                id,
//                video,
//                voteAverage,
//                title,
//                popularity,
//                posterPath,
//                originalLanguage,
//                originalTitle,
//                genreIds,
//                backdropPath,
//                adult,
//                overview,
//                releaseDate,
//                false
//        )
    }
}
