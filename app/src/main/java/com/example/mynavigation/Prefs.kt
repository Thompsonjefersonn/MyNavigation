package com.example.mynavigation

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs(private val ctx: Context) {
    private val prefs = ctx.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        const val KEY_BAHAN = "dt_bahan"
        const val KEY_CART = "dt_cart"
        const val KEY_BOUGHT = "dt_bought"
    }

    fun <T> saveList(key: String, list: List<T>) {
        val json = gson.toJson(list)
        prefs.edit().putString(key, json).apply()
    }

    // Generic function tanpa inline/reified — aman untuk publik
    fun <T> getList(key: String): MutableList<T> {
        val json = prefs.getString(key, null) ?: return mutableListOf()
        return try {
            val type = object : TypeToken<MutableList<T>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    // Alternatif: jika ingin memanggil dengan Class<T>
    fun <T> getList(key: String, clazz: Class<T>): MutableList<T> {
        val json = prefs.getString(key, null) ?: return mutableListOf()
        return try {
            val type = TypeToken.getParameterized(MutableList::class.java, clazz).type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            mutableListOf()
        }
    }
}
