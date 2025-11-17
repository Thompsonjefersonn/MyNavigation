package com.example.mynavigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter(
    private val onBoughtClick: (Bahan, Boolean) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartItems = listOf<Bahan>()

    fun submitList(list: List<Bahan>) {
        cartItems = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position], onBoughtClick)
    }

    override fun getItemCount(): Int = cartItems.size

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivBahan: ImageView = itemView.findViewById(R.id.iv_bahan)
        private val tvNamaBahan: TextView = itemView.findViewById(R.id.tv_nama_bahan)
        private val cbBought: CheckBox = itemView.findViewById(R.id.cb_bought)

        fun bind(bahan: Bahan, onBoughtClick: (Bahan, Boolean) -> Unit) {
            tvNamaBahan.text = bahan.nama
            Glide.with(itemView.context)
                .load(bahan.imageUrl)
                .placeholder(R.drawable.iconchefheader)
                .into(ivBahan)

            cbBought.setOnCheckedChangeListener { _, isChecked ->
                onBoughtClick(bahan, isChecked)
            }
        }
    }
}