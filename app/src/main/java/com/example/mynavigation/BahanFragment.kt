package com.example.mynavigation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BahanFragment : Fragment() {

    private lateinit var rvBahan: RecyclerView
    private lateinit var adapter: BahanAdapter
    private lateinit var prefs: Prefs
    private val daftarBahan = mutableListOf<Bahan>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bahan, container, false)
        rvBahan = view.findViewById(R.id.rvBahan)
        prefs = Prefs(requireContext())

        setupRecyclerView()
        loadData()

        view.findViewById<View>(R.id.btnTambahBahan).setOnClickListener { showAddDialog() }

        return view
    }

    private fun setupRecyclerView() {
        adapter = BahanAdapter { bahan ->
            addToCart(bahan)
        }
        rvBahan.layoutManager = LinearLayoutManager(requireContext())
        rvBahan.adapter = adapter
    }

    private fun loadData() {
        val savedBahan: MutableList<Bahan> = prefs.getList(Prefs.KEY_BAHAN, Bahan::class.java)
        if (savedBahan.isNotEmpty()) {
            daftarBahan.addAll(savedBahan)
        } else {
            val initialBahanNames = resources.getStringArray(R.array.initial_bahan_array)
            val initialBahanList = initialBahanNames.map { Bahan(it, "") } // Empty image url for now
            daftarBahan.addAll(initialBahanList)
            prefs.saveList(Prefs.KEY_BAHAN, daftarBahan)
        }
        adapter.submitList(daftarBahan)
    }

    private fun addToCart(bahan: Bahan) {
        val cartItems: MutableList<Bahan> = prefs.getList(Prefs.KEY_CART, Bahan::class.java)
        if (!cartItems.any { it.nama == bahan.nama }) {
            cartItems.add(bahan)
            prefs.saveList(Prefs.KEY_CART, cartItems)
            Toast.makeText(requireContext(), "${bahan.nama} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "${bahan.nama} sudah ada di keranjang", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_tambah_bahan, null)
        val etNamaBahan = dialogView.findViewById<EditText>(R.id.etNamaBahan)
        val etImageUrl = dialogView.findViewById<EditText>(R.id.etImageUrl)

        AlertDialog.Builder(requireContext())
            .setTitle("Tambah Bahan")
            .setView(dialogView)
            .setPositiveButton("Simpan") { dialog, _ ->
                val nama = etNamaBahan.text.toString().trim()
                val imageUrl = etImageUrl.text.toString().trim()
                if (nama.isNotEmpty()) {
                    val newBahan = Bahan(nama, imageUrl)
                    addBahan(newBahan)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun addBahan(bahan: Bahan) {
        daftarBahan.add(bahan)
        prefs.saveList(Prefs.KEY_BAHAN, daftarBahan)
        adapter.submitList(daftarBahan)
    }
}