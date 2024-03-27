package com.mycompany.moviemania2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class searchBar : AppCompatActivity() {
    lateinit var query2: EditText
    lateinit var showList: ArrayList<Int>
    var nop = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_bar)
//      var i=intent
//      var k=  findViewById<ImageView>(R.id.imp)
//        var get=getData(i.getIntExtra("pos",0))
//        get.setPoster(k)


        showList = arrayListOf(1)
        var button = findViewById<Button>(R.id.button)
        button.setOnClickListener(View.OnClickListener {
            var parent = findViewById<LinearLayout>(R.id.adder)
            query2 = findViewById(R.id.query)


            val searchQuery = query2.text.toString().trim()
            hideKeyboard(searchQuery)
        })


    }

    fun hideKeyboard(nameof:String) {
nop=3;
        var parent = findViewById<LinearLayout>(R.id.adder)

        parent.removeAllViews()
        var query = query2.text.toString().trim()
        val inflater = LayoutInflater.from(this)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("please wait while data is fetching")
        progressDialog.show()
        GlobalScope.launch(Dispatchers.Main)
        {
            try {
                var shows = RetrofitClient.instance.searchShows(nameof)
                if(shows.size<2) {

                    val builder = AlertDialog.Builder(this@searchBar)
                    builder.setTitle("Sorry")
                        .setMessage("No results found")
                    builder.setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()


                    progressDialog.dismiss()



                }
                for (i in 0 until shows.size) {
                    if(!showList.contains(shows[i].show.id))
                    showList.add(shows[i].show.id);
                }
            } catch (e: Exception) {
                Toast.makeText(this@searchBar, "error", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()

                val builder = AlertDialog.Builder(this@searchBar)
                builder.setTitle("Sorry")
                    .setMessage("No results found")
                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()






            }
        }
        for (i in 1 until showList.size) {
            var card = inflater.inflate(R.layout.listelement, null)

            var test = card.findViewById<TextView>(R.id.Head)
            var country = card.findViewById<TextView>(R.id.country)
            var genre = card.findViewById<TextView>(R.id.test)
            var img = card.findViewById<ImageView>(R.id.thumb)
            GlobalScope.launch(Dispatchers.Main)
            {
                try {

                    var show = RetrofitClient.instance.getShowDetails(showList[i])
                    Picasso.get().load(show.image.original).into(img)
                    test.setText(show.name)
                    genre.setText(show.genres[0] + " . " + show.runtime + " min")
                    country.setText(show.language)
                    progressDialog.dismiss()
                } catch (e: Exception) {
                    nop+=1
                    card.visibility = View.GONE

                }
            }

            card.setOnClickListener(View.OnClickListener {

                val sharedPref: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPref.edit()
               var rsearch= sharedPref.getString("rsearch","").toString()
                if(!rsearch.contains(""+showList[i]))
                rsearch="${showList[i]}#"+rsearch
                editor.putString("rsearch",rsearch)
                editor.apply()











                var k = Intent(this, detail::class.java)
                k.putExtra("pos", showList[i].toInt())
                startActivity(k)


            })

            card.findViewById<LinearLayout>(R.id.delete).visibility = View.GONE
            parent.addView(card)
        }
        //showList.clear()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)


    }

}


