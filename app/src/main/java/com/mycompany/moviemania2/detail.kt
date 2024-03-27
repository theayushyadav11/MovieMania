package com.mycompany.moviemania2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.parseAsHtml
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
var nop=0
lateinit var lists:ImageView
lateinit var listm:TextView
lateinit var list:String
lateinit var arList:ArrayList<String>
class detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var intent=intent
        var showId=intent.getIntExtra("pos",0)
        val sharedPref: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        list= sharedPref.getString("list","").toString()
        arList=ArrayList(list.split("#").toList())
        lists=findViewById(R.id.lists)
        listm=findViewById(R.id.listm)
        if(arList.contains(""+showId))
        {
            lists.setImageResource(R.drawable.bin_svgrepo_com)
            listm.setText("Remove from list")
            nop=1;
        }

//        editor.putString("list","")
//        editor.apply()
//arList.clear()


        GlobalScope.launch(Dispatchers.Main)
        {
            try {
                val show=RetrofitClient.instance.getShowDetails(showId)
                Picasso.get().load(show.image.medium).into(findViewById<ImageView>(R.id.background))
               findViewById<TextView>(R.id.genere).setText(show.genres[0])
               findViewById<TextView>(R.id.name).setText(show.name)
               findViewById<TextView>(R.id.duration).setText(""+show.averageRuntime+" min")
               findViewById<TextView>(R.id.summary).setText(""+show.summary.parseAsHtml().trim())


                val crew=RetrofitClient.instance.getCrewDetails(showId)
                findViewById<TextView>(R.id.crewt1).setText(crew[0].type)
                findViewById<TextView>(R.id.crewname1).setText(crew[0].person.name)
                findViewById<TextView>(R.id.crewt2).setText(crew[1].type)
                findViewById<TextView>(R.id.crewname2).setText(crew[1].person.name)
                findViewById<TextView>(R.id.crewt3).setText(crew[2].type)
                findViewById<TextView>(R.id.crewname3).setText(crew[2].person.name)
            }
            catch (e: Exception) {}
        }
        addcast()
        addnext()


    }
    fun addcast()
    {
        val inflater = LayoutInflater.from(this)

        for (i in 0 until 5)
        {
            val card = inflater.inflate(R.layout.cast, null)
            val parent=findViewById<LinearLayout>(R.id.castadder)
            var img=card.findViewById<ImageView>(R.id.img)
            var name=card.findViewById<TextView>(R.id.name)

            GlobalScope.launch(Dispatchers.Main)
            {
                try {
                    val cast=RetrofitClient.instance.getCastDetails(intent.getIntExtra("pos",0))
                    Picasso.get().load(cast[i].person.image.original).into(img)
                 name.setText(cast[i].person.name)
                }
                catch (e: Exception) {}
            }
            card.setOnClickListener(View.OnClickListener {

//                var k= Intent(this,detail::class.java)
//                k.putExtra("pos",fyl[i])
//                startActivity(k)

            })




            parent.addView(card)
        }



    }
    fun addnext()
    {
        val inflater = LayoutInflater.from(this)

        for (i in 0 until 5)
        {
            val card = inflater.inflate(R.layout.card, null)
            val parent=findViewById<LinearLayout>(R.id.nextadder)
            var img=card.findViewById<ImageView>(R.id.img)
            var name=card.findViewById<TextView>(R.id.name)

            GlobalScope.launch(Dispatchers.Main)
            {
                try {
                    val show=RetrofitClient.instance.getShowDetails(intent.getIntExtra("pos",0)+i+1)
                    Picasso.get().load(show.image.original).into(img)
                    name.setText(show.name)
                }
                catch (e: Exception) {}
            }
            card.setOnClickListener(View.OnClickListener {

                var k= Intent(this,detail::class.java)
                k.putExtra("pos",intent.getIntExtra("pos",0)+i+1)
                startActivity(k)

            })




            parent.addView(card)
        }



    }
    fun back(v:View) {
        onBackPressed()

    }
    fun addToList(v:View) {
        var id=""+intent.getIntExtra("pos",0)
        val sharedPref: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        list= sharedPref.getString("list","").toString()
        if(nop++%2==1)
        {

            list=list.replace("$id#","")

            lists.setImageResource(R.drawable.download)
            listm.setText("Add to list")
            editor.putString("list",list)
            editor.apply()
            Toast.makeText(this, "Removed from the List", Toast.LENGTH_SHORT).show()


        }
        else
        {
            list="$id#"+list
            lists.setImageResource(R.drawable.bin_svgrepo_com)
            listm.setText("Remove from list")
            editor.putString("list",list)
            editor.apply()
            Toast.makeText(this, "Added to the List", Toast.LENGTH_SHORT).show()


        }
    }
    override fun onBackPressed()
    {

        nop=0;
       startActivity(Intent(this@detail,MainActivity::class.java))
        super.onBackPressed()

    }
}