package uz.ferganagroup.uzblogs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.post_item_layout.view.*
import uz.ferganagroup.uzblogs.R
import uz.ferganagroup.uzblogs.model.PostModel

class PostAdapter(val items: List<PostModel>): RecyclerView.Adapter<PostAdapter.ItemHolder>(){

    inner class ItemHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.itemView.tvTitle.text = item.text
        holder.itemView.tvDate.text = item.publishDate
        Glide.with(holder.itemView.context).load(item.image).into(holder.itemView.imgPost)
    }
}