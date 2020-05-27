package com.ashapps.searchplot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.plot_item.view.*

class PlotAdapter(private val plotList: MutableList<PlotItem>) :
    RecyclerView.Adapter<PlotAdapter.PlotViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlotViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.plot_item,
            parent, false
        )
        return PlotViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlotViewHolder, position: Int) {
        val currentItem = plotList[position]
        loadImage(currentItem.imageUrl, holder.imageViewPlotItem)
        holder.titleItemTextView.text = currentItem.title
        holder.yearItemTextView.text = currentItem.year.toString()
    }

    override fun getItemCount() = plotList.size
    private fun loadImage(url: String?, imageView: ImageView?) {
        Picasso.get()
            .load(url)
            .resize(40, 40)
            .into(imageView)

    }

    class PlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPlotItem: ImageView = itemView.image_view_plot_item
        val titleItemTextView: TextView = itemView.title_item_text_view
        val yearItemTextView: TextView = itemView.year_item_text_view

    }
}