package com.example.retrofit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit.databinding.ActivityUserDetailBinding
import com.example.retrofit.model.Data
import com.example.retrofit.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private var employeeId: Int = -1
    private var currentEmployee: Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeId = intent.getIntExtra("id", -1)

        if (employeeId == -1) {
            Toast.makeText(this, "ID Employee tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Tombol Edit: Buka EditEmployeeActivity membawa data saat ini
        binding.btnEdit.setOnClickListener {
            currentEmployee?.let { emp ->
                val intent = Intent(this@UserDetailActivity, EditEmployeeActivity::class.java)
                intent.putExtra("id", emp.id)
                intent.putExtra("name", emp.employeeName)
                intent.putExtra("salary", emp.employeeSalary)
                intent.putExtra("age", emp.employeeAge)
                startActivity(intent)
            } ?: run {
                Toast.makeText(this, "Data belum dimuat", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol Delete: Konfirmasi dialog sebelum hapus
        binding.btnDelete.setOnClickListener {
            showDeleteDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        // Ambil data terbaru dari API saat Activity pertama dibuka atau setelah kembali dari Edit
        loadUserDetail()
    }

    private fun loadUserDetail() {
        ApiClient.getInstance().getUserDetail("eq.$employeeId").enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful) {
                    val empList = response.body()
                    if (!empList.isNullOrEmpty()) {
                        val emp = empList[0]
                        currentEmployee = emp

                        binding.tvId.text = "ID: ${emp.id}"
                        binding.tvName.text = "Name: ${emp.employeeName}"
                        binding.tvAge.text = "Age: ${emp.employeeAge}"
                        binding.tvSalary.text = "Salary: $ ${emp.employeeSalary}"
                    } else {
                        Toast.makeText(this@UserDetailActivity, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@UserDetailActivity, "Gagal mengambil data: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Toast.makeText(this@UserDetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("Hapus Data")
            .setMessage("Apakah Anda yakin ingin menghapus data ini?")
            .setPositiveButton("Hapus") { _, _ -> deleteDataFromApi() }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun deleteDataFromApi() {
        ApiClient.getInstance().deleteEmployee("eq.$employeeId").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UserDetailActivity, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show()
                    finish() // Kembali ke MainActivity
                } else {
                    Toast.makeText(this@UserDetailActivity, "Gagal hapus: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UserDetailActivity, "Error koneksi: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}