package com.mycompany.moviemania2

// TvMazeApiService.kt

import com.mycompany.moviemania2.cast.Cast
import com.mycompany.moviemania2.crew.Crew
import com.mycompany.moviemania2.image.Img
import com.mycompany.moviemania2.search.Search
import com.mycompany.moviemania2.show.Show
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeApiService {
    @GET("shows/{showId}")
    suspend fun getShowDetails(@Path("showId") showId: Int): Show
    @GET("shows/{showId}/cast")
    suspend fun getCastDetails(@Path("showId") personId: Int):Cast
    @GET("shows/{showId}/crew")
    suspend fun getCrewDetails(@Path("showId") personId: Int): Crew
    @GET("shows/{showId}/images")
    suspend fun getImages(@Path("showId") personId: Int): Img
    @GET("search/shows")
    suspend fun searchShows(@Query("q") query: String): Search
}
