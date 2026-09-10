package com.example.activity_intent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.activity_intent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivityLifecycle"
    private lateinit var binding: ActivityMainBinding
    companion object{
        const val EXTRA_NAME = "extra_name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            btnToSecondActivity.setOnClickListener {
                val intentToSecondActivity =
                    Intent(this@MainActivity, SecondActivity::class.java)

                intentToSecondActivity.putExtra("extra_name", edtName.text.toString())
                startActivity(intentToSecondActivity)
            }
        }
        Log.d(TAG, "onCreate: dipanggil")
    }
}