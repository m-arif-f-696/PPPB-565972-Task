package com.example.retrofit

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.model.Data
import com.example.retrofit.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userList = ArrayList<String>()
    private val userListId = ArrayList<Int>()
    private lateinit var listAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi adapter sekali di onCreate
        listAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            userList
        )
        binding.lvNama.adapter = listAdapter

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEmployeeActivity::class.java)
            startActivity(intent)
        }

        binding.lvNama.setOnItemClickListener { _, _, position, _ ->
            if (position < userListId.size) {
                val selectedId = userListId[position]
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                intent.putExtra("id", selectedId)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        ApiClient.getInstance().getAllUsers().enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    val listData = response.body()
                    if (!listData.isNullOrEmpty()) {
                        // Bersihkan dan isi ulang list HANYA ketika response sudah sukses
                        userList.clear()
                        userListId.clear()

                        for (i in listData) {
                            userList.add(i.employeeName)
                            i.id?.let { userListId.add(it) }
                        }

                        // Beri tahu adapter bahwa data telah berubah
                        listAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Koneksi error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}