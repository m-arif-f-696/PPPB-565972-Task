package com.example.retrofit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit.databinding.ActivityAddEmployeeBinding
import com.example.retrofit.model.Data
import com.example.retrofit.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val salary = binding.etSalary.text.toString().trim()
            val ageString = binding.etAge.text.toString().trim()

            if (name.isEmpty() || salary.isEmpty() || ageString.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val age = ageString.toIntOrNull() ?: 0

            val newEmployee = Data(
                employeeName = name,
                employeeSalary = salary,
                employeeAge = age
            )

            sendDataToApi(newEmployee)
        }
    }

    private fun sendDataToApi(employee: Data) {
        binding.btnSave.isEnabled = false // Cegah double submit

        ApiClient.getInstance().createEmployee(employee).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                binding.btnSave.isEnabled = true
                if (response.isSuccessful) {
                    Toast.makeText(this@AddEmployeeActivity, "Berhasil menambah data!", Toast.LENGTH_SHORT).show()
                    finish() // Kembali ke MainActivity
                } else {
                    Toast.makeText(this@AddEmployeeActivity, "Gagal simpan: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                binding.btnSave.isEnabled = true
                Toast.makeText(this@AddEmployeeActivity, "Error koneksi: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}