package com.mycompany.moviemania2
import com.mycompany.moviemania2.RetrofitClient


import android.util.Log
import android.widget.ImageView
import com.mycompany.moviemania2.cast.Cast
import com.mycompany.moviemania2.crew.Crew
import com.mycompany.moviemania2.search.Search
import com.mycompany.moviemania2.show.Show
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class getData {
    lateinit  var show:Show
    lateinit  var cast: Cast
    lateinit  var search:Search
    lateinit  var crew:Crew
    var showId=0
    var searchQuery=""
    constructor(showId:Int)
    {
        this.showId=showId
    }
    constructor(searchQuery:String)
    {
        this.searchQuery = searchQuery
    }
    fun create()
    {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                show = RetrofitClient.instance.getShowDetails(showId)
                cast = RetrofitClient.instance.getCastDetails(showId)
                crew = RetrofitClient.instance.getCrewDetails(showId)
                search=RetrofitClient.instance.searchShows(searchQuery)
            } catch (e: Exception) {
                Log.d("error", "main: error")
            }
        }
    }
    fun setPoster(imageView: ImageView)
    {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                show = RetrofitClient.instance.getShowDetails(showId)
            } catch (e: Exception) {
                Log.d("error", "main: error")
            }
            Picasso.get().load(show.image.original).into(imageView)
        }
    }
} 