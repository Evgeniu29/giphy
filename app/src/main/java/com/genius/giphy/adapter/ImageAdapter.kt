package com.genius.giphy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.genius.giphy.R

class ImageAdapter(private val context: Context, private val array: List<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

            return ViewHolder(

                LayoutInflater.from(context)
                    .inflate(R.layout.gift_item2, parent, false)
            )

        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  data = array[position]

        Glide.with(context).load(data).into(holder.imageView2)

    }

    override fun getItemCount(): Int {
        return array.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val imageView2: ImageView = itemView.findViewById<ImageView>(R.id.image2)


    }

}
