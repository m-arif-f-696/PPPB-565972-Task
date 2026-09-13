package com.example.instagram_clone

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagram_clone.databinding.ActivityProfileDetailBinding


private lateinit var binding: ActivityProfileDetailBinding
class ProfileDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("extra_username")

        with(binding) {
            tvNama.text = username
            btnKembali.setOnClickListener {
                finish()
            }
        }

    }
}