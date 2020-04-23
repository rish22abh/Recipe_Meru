package com.recipe.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.R
import com.recipe.model_db.Recipes

class RecyclerViewAdapter(
    private var mContext: Context,
    private var listRecipe: ArrayList<Recipes>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.adapter_vertical_layout,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return listRecipe.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(mContext).load(listRecipe[position].image_url)
            .into((holder as ItemViewHolder).imageView)
        holder.textTitle.text = Html.fromHtml(listRecipe[position].title)
        holder.textDesc.text = listRecipe[position].publisher
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
        var textTitle: TextView = itemView.findViewById(R.id.textTitle)
        var textDesc: TextView = itemView.findViewById(R.id.textDesc)
    }
}

