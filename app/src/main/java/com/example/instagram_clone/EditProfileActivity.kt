package com.example.instagram_clone

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DoNotInline
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagram_clone.databinding.ActivityEditProfileBinding
import com.example.instagram_clone.databinding.ActivityProfileDetailBinding

private lateinit var binding: ActivityEditProfileBinding
class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("extra_username")
        val bio = intent.getStringExtra("extra_bio")

        with(binding){
            // Gunakan .setText() untuk EditText
            etNama.setText(username)
            etBio.setText(bio)

            // Logika tombol simpan untuk mengirim balik data ke MainActivity
            btnSimpan.setOnClickListener {
                val updatedNama = etNama.text.toString()
                val updatedBio = etBio.text.toString()

                val resultIntent = Intent().apply {
                    putExtra("extra_username", updatedNama)
                    putExtra("extra_bio", updatedBio)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }

        }



    }
}