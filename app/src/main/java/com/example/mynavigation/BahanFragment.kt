package com.example.mynavigation

import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class BahanFragment : Fragment() {

    private lateinit var lvBahan: ListView
    private lateinit var btnTambah: Button

    // data awal ditaruh sederhana
    private val data = mutableListOf(
        "Gula (Bumbu)",
        "Ayam (Protein)",
        "Tomat (Sayur)"
    )

    private lateinit var adapter: ArrayAdapter<String>
    private val categories = arrayOf("Sayur", "Buah", "Bumbu", "Protein", "Lainnya")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_bahan, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lvBahan = view.findViewById(R.id.lvBahan)
        btnTambah = view.findViewById(R.id.btnTambahBahan)


        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, data)
        lvBahan.adapter = adapter


        btnTambah.setOnClickListener { showAddDialog() }


        lvBahan.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(requireContext(), data[position], Toast.LENGTH_SHORT).show()
        }


        val gestureDetector = GestureDetector(
            requireContext(),
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    val pos = lvBahan.pointToPosition(e.x.toInt(), e.y.toInt())
                    if (pos != ListView.INVALID_POSITION) showActionDialog(pos)
                    return true
                }
            })

        lvBahan.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            false
        }
    }

    private fun showAddDialog() {
        val etName = EditText(requireContext())
        etName.hint = "Nama Bahan"

        AlertDialog.Builder(requireContext())
            .setTitle("Tambah Bahan")
            .setView(etName)
            .setPositiveButton("Simpan") { _, _ ->
                val name = etName.text.toString().trim()
                if (name.isNotEmpty()) {
                    showCategoryDialog { cat ->
                        data.add("$name ($cat)")
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showActionDialog(position: Int) {
        val selected = data[position]
        val options = arrayOf("Hapus", "Ganti Kategori")

        AlertDialog.Builder(requireContext())
            .setTitle(selected)
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> { data.removeAt(position); adapter.notifyDataSetChanged() }
                    1 -> changeCategory(position)
                }
                dialog.dismiss()
            }
            .show()
    }

    private fun changeCategory(position: Int) {
        showCategoryDialog { cat ->
            val nameOnly = data[position].substringBefore(" (")
            data[position] = "$nameOnly ($cat)"
            adapter.notifyDataSetChanged()
        }
    }

    private fun showCategoryDialog(onSelected: (String) -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("Pilih Kategori")
            .setItems(categories) { d, which ->
                onSelected(categories[which])
                d.dismiss()
            }
            .show()
    }
}
