package com.example.activity_intent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.activity_intent.databinding.ActivitySecondBinding
import com.example.activity_intent.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThirdBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding) {
            btnToSecondActivity.setOnClickListener {
                val resultIntent = Intent()
                val name = intent.getStringExtra("extra_name")
                // Memasukkan alamat ke Intent
                resultIntent.putExtra("extra_address", edtAddress.text.toString())
                resultIntent.putExtra("extra_name", name)
                // Menetapkan result code dan data Intent
                setResult(Activity.RESULT_OK, resultIntent)
                // Menyelesaikan aktivitas
                finish()
            }

        }

    }
}