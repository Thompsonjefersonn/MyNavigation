package com.example.mynavigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BahanAdapter(private val onCartClick: (Bahan) -> Unit) : RecyclerView.Adapter<BahanAdapter.BahanViewHolder>() {

    private var bahanList = listOf<Bahan>()

    fun submitList(list: List<Bahan>) {
        bahanList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BahanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bahan, parent, false)
        return BahanViewHolder(view)
    }

    override fun onBindViewHolder(holder: BahanViewHolder, position: Int) {
        holder.bind(bahanList[position], onCartClick)
    }

    override fun getItemCount(): Int = bahanList.size

    class BahanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivBahan: ImageView = itemView.findViewById(R.id.iv_bahan)
        private val tvNamaBahan: TextView = itemView.findViewById(R.id.tv_nama_bahan)
        private val ivCart: ImageView = itemView.findViewById(R.id.iv_cart)

        fun bind(bahan: Bahan, onCartClick: (Bahan) -> Unit) {
            tvNamaBahan.text = bahan.nama
            Glide.with(itemView.context)
                .load(bahan.imageUrl)
                .placeholder(R.drawable.iconchefheader) // Add a placeholder drawable
                .into(ivBahan)

            ivCart.setOnClickListener { onCartClick(bahan) }
        }
    }
}