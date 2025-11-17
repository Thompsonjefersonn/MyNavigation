package com.example.mynavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartFragment : Fragment() {

    private lateinit var rvCart: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var prefs: Prefs
    private var cartItems = mutableListOf<Bahan>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        prefs = Prefs(requireContext())
        rvCart = view.findViewById(R.id.rvCart)
        setupRecyclerView()
        loadCartItems()
        return view
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter { bahan, isChecked ->
            if (isChecked) {
                markAsBought(bahan)
            }
        }
        rvCart.layoutManager = LinearLayoutManager(requireContext())
        rvCart.adapter = adapter
    }

    private fun loadCartItems() {
        cartItems = prefs.getList(Prefs.KEY_CART, Bahan::class.java)
        adapter.submitList(cartItems)
    }

    private fun markAsBought(bahan: Bahan) {
        // Add to bought list
        val boughtItems: MutableList<Bahan> = prefs.getList(Prefs.KEY_BOUGHT, Bahan::class.java)
        if (!boughtItems.any { it.nama == bahan.nama }) {
            boughtItems.add(bahan)
            prefs.saveList(Prefs.KEY_BOUGHT, boughtItems)
        }

        // Remove from cart list
        val updatedCartItems = cartItems.filter { it.nama != bahan.nama }.toMutableList()
        prefs.saveList(Prefs.KEY_CART, updatedCartItems)
        cartItems = updatedCartItems
        adapter.submitList(cartItems)

        Toast.makeText(requireContext(), "${bahan.nama} sudah dibeli", Toast.LENGTH_SHORT).show()
    }
}