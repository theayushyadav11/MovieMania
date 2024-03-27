package com.mycompany.moviemania2

import android.content.Context
import android.content.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageAdapter(private val context: Context,private val imageList: ArrayList<Int>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
//        view.setOnClickListener(View.OnClickListener {
//            Toast.makeText(parent.context, ""+viewType, Toast.LENGTH_SHORT).show()

//        })
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        GlobalScope.launch(Dispatchers.Main) {
            try {

                val show = RetrofitClient.instance.getShowDetails(imageList[position])
                Picasso.get().load(show.image.medium).into(holder.imageView)
                holder.imageView.setOnClickListener {
                    var i=Intent(context,detail::class.java)
                    i.putExtra("pos",imageList[position%imageList.size])
                    startActivity(context,i,null)
                }

            } catch (e: Exception) {

            }
        }

        if (position == imageList.size-1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}