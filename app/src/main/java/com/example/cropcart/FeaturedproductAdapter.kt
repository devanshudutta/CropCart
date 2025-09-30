package com.example.cropcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FeaturedproductAdapter(
    private val itemList: List<featured_product>,
    private val onItemClick: (featured_product) -> Unit
) : RecyclerView.Adapter<FeaturedproductAdapter.FeaturedViewHolder>() {

    inner class FeaturedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.featuredImage)
        val name: TextView = itemView.findViewById(R.id.featuredName)
        val price: TextView = itemView.findViewById(R.id.featuredPrice)
        val description: TextView = itemView.findViewById(R.id.featuredDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.featured_item, parent, false)
        return FeaturedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        val item = itemList[position]

        holder.name.text = item.name
        holder.price.text = item.price
        holder.description.text = item.description

        // Load image with Glide, fallback to placeholder
        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(R.drawable.img)
            .error(R.drawable.img)
            .into(holder.image)

        // Click listener
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = itemList.size
}
