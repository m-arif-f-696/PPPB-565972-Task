package com.example.activity_intent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.activity_intent.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if(result.resultCode == Activity.RESULT_OK) {
            // Mengambil data Intent
            val data = result.data
            // Mendapatkan alamat dari data Intent
            val name = data?.getStringExtra("extra_name")
            val address = data?.getStringExtra("extra_address")

            //menetapkan teks di Text view
            binding.txtName.text = "$name beralamat di $address"
        }
    }

    companion object {
        const val EXTRA_ADDRESS = "extra_address"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(MainActivity.EXTRA_NAME)

        with(binding) {
            txtName.text = name
            btnToThirdActivity.setOnClickListener {
                val intent = Intent(this@SecondActivity, ThirdActivity::class.java).apply { putExtra(
                    MainActivity.EXTRA_NAME, name) }
                launcher.launch(intent)
            }
        }

    }
}