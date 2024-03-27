package com.mycompany.moviemania2

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.htmlEncode
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import com.bumptech.glide.Glide

import com.squareup.picasso.Picasso


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var  viewPager2: ViewPager2
    private lateinit var handler : Handler
    private lateinit var img : ImageView
    lateinit var img2: ImageView
    lateinit var imageList:ArrayList<Int>
    private lateinit var adapter: ImageAdapter
    lateinit var type:Array<TextView>
    val kids = intArrayOf(713,672,2560,35173,72278,45968)
    val jr = intArrayOf(53277,59825,63267,67128,37336,71431)
    val fyl = intArrayOf(18303,55679 ,15479,8514,70991,18777,7316)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        setUpTransformer()
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 2000)
            }
        })
GlobalScope.launch(Dispatchers.Main){
    try {
var i=0
        var v=RetrofitClient.instance.searchShows("indian idol")
        Picasso.get().load(v[i].show.image.original).into(img)
        var c=findViewById<TextView>(R.id.id)
        c.setText(v[i].show.id.toString())
        findViewById<TextView>(R.id.names).setText(v[i].show.name+" "+v[i].show.premiered+" "+v[i].show.ended)

    }
    catch (e: Exception) {
        Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
    }
}
        addjr()
addKids()
        addfyl()





    }
    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable , 2000)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f

        }

        viewPager2.setPageTransformer(transformer)
    }
    private fun init(){
        viewPager2 = findViewById(R.id.viewPager2)
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()
        img=findViewById(R.id.img)

        type=Array(4){ TextView(this) }
        type[0]=findViewById(R.id.type0)
        type[1]=findViewById(R.id.type1)
        type[2]=findViewById(R.id.type2)
        type[3]=findViewById(R.id.type3)
        imageList.add(82)
        imageList.add( 169)
        imageList.add(431 )
        imageList.add(3594 )
        imageList.add( 526)
        imageList.add( 83)
        imageList.add( 43687)
        imageList.add( 47199)


        adapter = ImageAdapter(this,imageList, viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }
    fun addKids()
    {
        val inflater = LayoutInflater.from(this)

        for (i in 0 until kids.size)
        {
            val card = inflater.inflate(R.layout.card, null)
            val parent=findViewById<LinearLayout>(R.id.kidadder)
            var img=card.findViewById<ImageView>(R.id.img)
            var name=card.findViewById<TextView>(R.id.name)
            GlobalScope.launch(Dispatchers.Main)
            {
                try {
                    val show=RetrofitClient.instance.getShowDetails(kids[i])
                    Picasso.get().load(show.image.original).into(img)
                    name.setText(show.name)
                }
                catch (e: Exception) {}
            }
         card.setOnClickListener(View.OnClickListener {

             var k= Intent(this,detail::class.java)
             k.putExtra("pos",kids[i])
             startActivity(k)

         })




            parent.addView(card)
        }



    }
    fun addjr()
    {
        val inflater = LayoutInflater.from(this)

        for (i in 0 until jr.size)
        {
            val card = inflater.inflate(R.layout.card, null)
            val parent=findViewById<LinearLayout>(R.id.adder)
            var img=card.findViewById<ImageView>(R.id.img)
            var name=card.findViewById<TextView>(R.id.name)
            GlobalScope.launch(Dispatchers.Main)
            {
                try {
                    val show=RetrofitClient.instance.getShowDetails(jr[i])
                    Picasso.get().load(show.image.original).into(img)
                    name.setText(show.name)
                }
                catch (e: Exception) {}
            }
            card.setOnClickListener(View.OnClickListener {

                var k= Intent(this,detail::class.java)
                k.putExtra("pos",jr[i])
                startActivity(k)

            })




            parent.addView(card)
        }



    }

    fun addfyl()
    {
        val inflater = LayoutInflater.from(this)

        for (i in 0 until fyl.size)
        {
            val card = inflater.inflate(R.layout.card, null)
            val parent=findViewById<LinearLayout>(R.id.fyl)
            var img=card.findViewById<ImageView>(R.id.img)
            var name=card.findViewById<TextView>(R.id.name)
            GlobalScope.launch(Dispatchers.Main)
            {
                try {
                    val show=RetrofitClient.instance.getShowDetails(fyl[i])
                    Picasso.get().load(show.image.original).into(img)
                    name.setText(show.name)
                }
                catch (e: Exception) {}
            }
            card.setOnClickListener(View.OnClickListener {

                var k= Intent(this,detail::class.java)
                k.putExtra("pos",fyl[i])
                startActivity(k)

            })




            parent.addView(card)
        }



    }
    fun mylist(v:View)
    {
        startActivity(Intent(this,ListClass::class.java))
    }
    fun search(v:View)
    {
        startActivity(Intent(this,searchBar::class.java))
    }
    fun recent(v:View)
    {
        startActivity(Intent(this,recent::class.java))
    }
    override fun onBackPressed() {
        //sjhsdjh
    }
}