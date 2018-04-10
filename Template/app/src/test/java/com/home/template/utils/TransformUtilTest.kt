package com.home.template.utils

import com.home.template.films.response.Film
import org.hamcrest.CoreMatchers.isA
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

import org.junit.Before
import ru.surfstudio.standard.interactor.films.network.response.FilmResponse

class TransformUtilTest {

    private lateinit var subject : TransformUtil

    @Before
    fun setUp() {
        subject = TransformUtil()
    }

    @Test
    fun testTransformCollection_fullCollection_collectionWithTransformedObjects() {
        val result = subject.transformCollection(prepareCollection())

        assertEquals(3, result.size)
        assertThat(result.get(0), isA(Film::class.java))
    }

    @Test
    fun testTransformCollection_emptyCollection_emptyCollection() {
        val result = subject.transformCollection(mutableListOf<FilmResponse>())

        assertEquals(0, result.size)
    }

    fun prepareCollection() : List<FilmResponse> {
        val result = mutableListOf<FilmResponse>()
        result.add(FilmResponse(1, 1))
        result.add(FilmResponse(1, 2))
        result.add(FilmResponse(1, 3))
        return result
    }

}