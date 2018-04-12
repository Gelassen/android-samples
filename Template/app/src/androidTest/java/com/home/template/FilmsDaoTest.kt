package com.home.template

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log

import com.home.template.films.storage.FilmsDaoJ
import com.home.template.films.storage.FilmsDatabaseJ
import com.home.template.films.storage.model.UserFilms
import com.home.template.films.storage.model.Film
import com.home.template.films.storage.model.User

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.assertEquals


@RunWith(AndroidJUnit4::class)
open class FilmsDaoTest {

    private var database: FilmsDatabaseJ? = null
    private var filmsDao: FilmsDaoJ? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        try {
            database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), FilmsDatabaseJ::class.java)
                    .allowMainThreadQueries()
                    .build()
            filmsDao = database!!.getFilmsDao()
        } catch (ex: Exception) {
            Log.e("TAG", "Failed to create database", ex)
        }

    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        database!!.close()
    }

    @Test
    fun testGetAllFilms_noFilms_emptyCollection() {
        val films = filmsDao!!.allFilm

        assertEquals(0, films.size.toLong())
    }

    @Test
    fun testInsertFilm_insertTwoFilms_twoFilmsInStorage() {
        val filmFirst = Film()
        filmFirst.id = 1
        filmFirst.overview = "first film"
        val filmSecond = Film()
        filmSecond.id = 2
        filmSecond.overview = "second film"

        filmsDao!!.insertAllFilms(filmFirst, filmSecond)

        val films = filmsDao!!.allFilm
        assertEquals(2, films.size)
    }

    @Test
    fun testDeleteFilm_deleteOneFilmWhenTwoInStorage_oneFilmInStorage() {
        val filmFirst = Film()
        filmFirst.id = 1
        filmFirst.overview = "first film"
        val filmSecond = Film()
        filmSecond.id = 2
        filmSecond.overview = "second film"
        filmsDao!!.insertAllFilms(filmFirst, filmSecond)

        filmsDao!!.delete(filmFirst)

        val films = filmsDao!!.allFilm
        assertEquals(1, films.size)
        assertEquals(2, films.get(0).id)
    }

    @Test
    fun testSearchByTitle_searchFilmWhenTwoFilmsHaveSimilarTitle_twoFilmsInResult() {
        val filmFirst = Film()
        filmFirst.id = 1
        filmFirst.title = "first film"
        val filmSecond = Film()
        filmSecond.id = 2
        filmSecond.title = "second film"
        val filmThird = Film()
        filmThird.id = 3
        filmThird.title = "third"
        filmsDao!!.insertAllFilms(filmFirst, filmSecond, filmThird)

        val result = filmsDao!!.getAllFilmWithTitle("%film%")

        assertEquals(2, result.size)
        assertEquals("second film", result.get(1).title)
    }

    @Test
    fun testGetFilmsWithUsers_threeFilmsAndTwoAssignedOnUser_twoFilmsInResult() {
        val userFirst = User()
        userFirst.id = 1
        userFirst.name = "first user"
        userFirst.description = "desc"
        val userSecond = User()
        userSecond.id = 2
        userSecond.name = "second user"
        userSecond.description = "desc"
        filmsDao!!.insertAllUsers(userFirst, userSecond)

        val filmFirst = Film()
        filmFirst.id = 1
        filmFirst.userId = 1
        filmFirst.title = "first film"
        val filmSecond = Film()
        filmSecond.id = 2
        filmSecond.title = "second film"
        val filmThird = Film()
        filmThird.id = 3
        filmThird.title = "third"
        filmsDao!!.insertAllFilms(filmFirst, filmSecond, filmThird)

        filmsDao!!.insertUserFilms(UserFilms(1, 1))
        filmsDao!!.insertUserFilms(UserFilms(1, 2))


        val films = filmsDao!!.getAllFilm()
        val users = filmsDao!!.getAllUser()
        val filmsWithUsers = filmsDao!!.getAllFilmsForUser(1)
        val userFilms = filmsDao!!.getAllUserFilms()

        assertEquals(3, films.size)
        assertEquals(2, users.size)
        assertEquals(2, userFilms.size)
        assertEquals(2, filmsWithUsers.size)
    }
}
