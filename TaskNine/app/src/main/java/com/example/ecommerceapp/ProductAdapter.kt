package com.example.ecommerceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val onCartClick: (Product) -> Unit) :
    ListAdapter<Product, RecyclerView.ViewHolder>(ProductDiffCallback()) {

    var isGridView: Boolean = false

    companion object {
        const val VIEW_TYPE_LIST = 1
        const val VIEW_TYPE_GRID = 2
        const val VIEW_TYPE_SKELETON = 3
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isLoading) VIEW_TYPE_SKELETON
        else if (isGridView) VIEW_TYPE_GRID
        else VIEW_TYPE_LIST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_SKELETON -> R.layout.item_skeleton
            VIEW_TYPE_GRID -> R.layout.item_product_grid
            else -> R.layout.item_product_list
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return when (viewType) {
            VIEW_TYPE_SKELETON -> SkeletonViewHolder(view)
            VIEW_TYPE_GRID -> GridViewHolder(view)
            else -> ListViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = getItem(position)
        when (holder) {
            is ListViewHolder -> holder.bind(product)
            is GridViewHolder -> holder.bind(product)
        }
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img = view.findViewById<ImageView>(R.id.imgProduct)
        private val name = view.findViewById<TextView>(R.id.tvName)
        private val category = view.findViewById<TextView>(R.id.tvCategory)
        private val rating = view.findViewById<RatingBar>(R.id.ratingBar)
        private val price = view.findViewById<TextView>(R.id.tvPrice)
        private val btn = view.findViewById<Button>(R.id.btnAddToCart)

        fun bind(product: Product) {
            name.text = product.name
            category.text = product.category
            rating.rating = product.rating
            price.text = itemView.context.getString(R.string.price_format, product.price)
            img.setImageResource(product.imageRes)
            btn.setOnClickListener { onCartClick(product) }
        }
    }

    inner class GridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val img = view.findViewById<ImageView>(R.id.imgProductGrid)
        private val name = view.findViewById<TextView>(R.id.tvNameGrid)
        private val price = view.findViewById<TextView>(R.id.tvPriceGrid)
        private val cartIcon = view.findViewById<ImageButton>(R.id.btnCartGrid)

        fun bind(product: Product) {
            name.text = product.name
            price.text = itemView.context.getString(R.string.price_format, product.price)
            img.setImageResource(product.imageRes)
            cartIcon.setOnClickListener { onCartClick(product) }
        }
    }

    inner class SkeletonViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}
