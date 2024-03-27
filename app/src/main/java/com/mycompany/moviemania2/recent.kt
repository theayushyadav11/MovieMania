package com.mycompany.moviemania2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class recent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent)




        val sharedPref: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()



        var lists=sharedPref.getString("rsearch" ,"")
        var  arList=ArrayList(lists?.split("#")?.toList() ?: emptyList())




        val inflater = LayoutInflater.from(this)

        for (i in 0 until arList.size-1)
        {
            val card = inflater.inflate(R.layout.listelement, null)
            var parent=findViewById<LinearLayout>(R.id.adder)
            var test=card.findViewById<TextView>(R.id.Head)
            var genre=card.findViewById<TextView>(R.id.test)
            var delete=card.findViewById<LinearLayout>(R.id.delete)
            test.setText(arList[i])
            var img=card.findViewById<ImageView>(R.id.thumb)
            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("please wait while data is fetching")
            progressDialog.show()
            GlobalScope.launch(Dispatchers.Main)
            {
                try {
                    val show=RetrofitClient.instance.getShowDetails(arList[i].toInt())
                    Picasso.get().load(show.image.original).into(img)
                    test.setText(show.name)
                    genre.setText(show.genres[0]+" . "+show.runtime+" min")
                    progressDialog.dismiss()
                }
                catch (e: Exception) {}
            }
            card.setOnClickListener(View.OnClickListener {

                var k= Intent(this,detail::class.java)
                k.putExtra("pos",arList[i].toInt())
                startActivity(k)

            })
            delete.setOnClickListener(View.OnClickListener {

                lists= lists?.replace(arList[i]+"#","")



                editor.putString("rsearch",lists)
                editor.apply()


                //parent.removeView(card)
                showDeleteConfirmationDialog(parent, card)
            })




            parent.addView(card)
        }







    }
    private fun showDeleteConfirmationDialog(parent:LinearLayout,card:View) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Delete Confirmation")
            setMessage("Are you sure you want to delete?")
            setPositiveButton("Yes") { dialog, which ->
                deleteItem(parent, card)
            }
            setNegativeButton("No") { dialog, which ->

            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteItem(parent:LinearLayout,card:View) {
        // Perform delete action
        parent.removeView(card)
        Toast.makeText(this, "Deleted successfully!", Toast.LENGTH_SHORT).show()
    }
    fun home(v:View)
    {
        startActivity(Intent(this,MainActivity::class.java))
    }
    fun search(v:View)
    {
        startActivity(Intent(this,searchBar::class.java))
    }
    fun mylist(v:View)
    {
        startActivity(Intent(this,ListClass::class.java))
    }


}