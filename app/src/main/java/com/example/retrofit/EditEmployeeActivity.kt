package com.example.retrofit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit.databinding.ActivityEditEmployeeBinding
import com.example.retrofit.model.Data
import com.example.retrofit.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditEmployeeBinding
    private var employeeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data intent dari UserDetailActivity
        employeeId = intent.getIntExtra("id", -1)
        val name = intent.getStringExtra("name") ?: ""
        val salary = intent.getStringExtra("salary") ?: ""
        val age = intent.getIntExtra("age", 0)

        // Isi form dengan data lama
        binding.etName.setText(name)
        binding.etSalary.setText(salary)
        binding.etAge.setText(age.toString())

        binding.btnUpdate.setOnClickListener {
            val updatedName = binding.etName.text.toString().trim()
            val updatedSalary = binding.etSalary.text.toString().trim()
            val updatedAgeString = binding.etAge.text.toString().trim()

            if (updatedName.isEmpty() || updatedSalary.isEmpty() || updatedAgeString.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedAge = updatedAgeString.toIntOrNull() ?: 0

            val updatedEmployee = Data(
                employeeName = updatedName,
                employeeSalary = updatedSalary,
                employeeAge = updatedAge
            )

            updateDataToApi(updatedEmployee)
        }
    }

    private fun updateDataToApi(employee: Data) {
        binding.btnUpdate.isEnabled = false

        // Format query parameter Supabase "eq.<id>"
        ApiClient.getInstance().updateEmployee("eq.$employeeId", employee)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    binding.btnUpdate.isEnabled = true
                    if (response.isSuccessful) {
                        Toast.makeText(this@EditEmployeeActivity, "Berhasil update data!", Toast.LENGTH_SHORT).show()
                        finish() // Kembali ke UserDetailActivity
                    } else {
                        Toast.makeText(this@EditEmployeeActivity, "Gagal update: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    binding.btnUpdate.isEnabled = true
                    Toast.makeText(this@EditEmployeeActivity, "Error koneksi: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}