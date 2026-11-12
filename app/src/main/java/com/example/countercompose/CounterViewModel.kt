package com.example.countercompose

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel

class CounterViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    // State Counter
    private val _counter = mutableIntStateOf(prefs.getInt(KEY_COUNTER, 0))
    val counter: State<Int> = _counter

    // State Form Input Nama (saat diketik)
    private val _namaInput = mutableStateOf("")
    val namaInput: State<String> = _namaInput

    // State Nama Tersimpan (tampil di bawah counter setelah klik Simpan)
    private val _savedName = mutableStateOf(prefs.getString(KEY_NAMA, "") ?: "")
    val savedName: State<String> = _savedName

    fun increment() = updateCounter(_counter.intValue + 1)
    fun decrement() = updateCounter(_counter.intValue - 1)
    fun reset() = updateCounter(0)

    private fun updateCounter(newValue: Int) {
        _counter.intValue = newValue
        prefs.edit { putInt(KEY_COUNTER, newValue) }
    }

    fun onNamaChange(newName: String) {
        _namaInput.value = newName
    }

    fun saveNama() {
        val trimmed = _namaInput.value.trim()
        if (trimmed.isNotEmpty()) {
            _savedName.value = trimmed
            prefs.edit { putString(KEY_NAMA, trimmed) }
        }
    }

    companion object {
        private const val PREFS_NAME = "counter_prefs"
        private const val KEY_COUNTER = "counter_value"
        private const val KEY_NAMA = "saved_nama"
    }
}