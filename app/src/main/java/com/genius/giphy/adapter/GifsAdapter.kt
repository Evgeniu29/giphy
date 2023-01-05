package com.genius.giphy.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.genius.giphy.data.local.GifItem
import com.genius.giphy.databinding.GiftItemBinding
import com.genius.giphy.ui.SecondActivity

var image: ArrayList<String> = ArrayList<String>()

class GifsAdapter( private val itemClickListener: Context
) :
    ListAdapter<GifItem, GifsAdapter.GifViewHolder>(object :
        DiffUtil.ItemCallback<GifItem>() {

        override fun areItemsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
            return oldItem == newItem
        }
    }) {


    var favourites: List<String> = emptyList()

        set(value) {
            if (field == value)


                return
            field = value

            notifyDataSetChanged()

        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val itemBinding = GiftItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = GifViewHolder(itemBinding)

        return holder
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener(){

            val intent = Intent( holder.itemView.context, SecondActivity::class.java)

            intent.putExtra("position", position)

            holder.itemView.context.startActivity(intent)

            listOf(holder.itemView.context)

        }

    }

    class GifViewHolder(private val itemBinding: GiftItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(gifItem: GifItem) {


            with(itemBinding) {

                Glide.with(itemView).load(gifItem.gifUrl).into(ivGif)

                image.add(gifItem.gifUrl)

            }


        }
    }


}

interface OnItemClickListener {
    fun onItemClicked(gifItem: GifItem, share: Boolean = false)

}