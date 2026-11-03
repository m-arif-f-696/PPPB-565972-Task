package com.example.disasterapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disasterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapterDisaster = DisasterAdapter(generateDummy()) { disaster ->
            Toast.makeText(this@MainActivity, "You clicked on ${disaster.nameDisaster}", Toast.LENGTH_SHORT).show()
        }
        with(binding) {

            rvDisaster.apply {
                adapter = adapterDisaster
                layoutManager = GridLayoutManager(this@MainActivity, 2)
            }
        }
    }

    fun generateDummy(): List<Disaster> {
        return listOf(
            Disaster(nameDisaster = "Tsunami", disasterType = "Natural", imgDisaster = R.drawable.tsunami),
            Disaster(nameDisaster = "Volcanic Eruption", disasterType = "Natural", imgDisaster = R.drawable.vulcano),
            Disaster(nameDisaster = "Earthquake", disasterType = "Natural", imgDisaster = R.drawable.earthquake),
            Disaster(nameDisaster = "Flood", disasterType = "Natural", imgDisaster = R.drawable.flood),
            Disaster(nameDisaster = "Fire", disasterType = "Natural", imgDisaster = R.drawable.fire),
            Disaster(nameDisaster = "Nuclear Accident", disasterType = "Man-made", imgDisaster = R.drawable.nuclear),
            Disaster(nameDisaster = "Terrorist Attack", disasterType = "Man-made", imgDisaster = R.drawable.terrorist),
            Disaster(nameDisaster = "War", disasterType = "Man-made", imgDisaster = R.drawable.war)
        )
    }
}
