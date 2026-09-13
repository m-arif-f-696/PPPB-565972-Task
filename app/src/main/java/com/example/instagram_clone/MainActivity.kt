package com.example.instagram_clone

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagram_clone.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {

    var username = "Arif"
    var textBio = "coba-coba"

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            // Simpan data baru dari Intent ke variabel
            username = data?.getStringExtra("extra_username") ?: username
            textBio = data?.getStringExtra("extra_bio") ?: textBio

            // update ui
            binding.profile.txtUsername.text = username
            binding.bio.bioText.text = textBio
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        with(binding) {
            profile.txtUsername.text = username
            bio.bioText.text = textBio

            profile.btnToProfileDetail.setOnClickListener {
                val intentToProfileActivity =
                    Intent(this@MainActivity, ProfileDetailActivity::class.java)
                        .apply {
                            putExtra("extra_username", username)
                        }
                startActivity(intentToProfileActivity)
            }

            bio.btnEdit.setOnClickListener {
                val intent = Intent(this@MainActivity, EditProfileActivity::class.java)
                    .apply {
                        putExtra("extra_username", username)
                        putExtra("extra_bio", textBio)
                    }
                launcher.launch(intent)
            }


        }

    }
}