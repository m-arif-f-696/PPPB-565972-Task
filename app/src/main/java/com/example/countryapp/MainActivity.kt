package com.example.countryapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import com.example.countryapp.databinding.ActivityMainBinding
import com.example.countryapp.databinding.DialogExitBinding
import java.util.Calendar

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var  binding: ActivityMainBinding
    private lateinit var provinces: Array<String>
    private val countries = arrayOf(
        "Indonesia",
        "United States",
        "United Kingdom",
        "Germany",
        "France",
        "Australia",
        "Japan",
        "China",
        "Brazil",
        "Canada"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        provinces = resources.getStringArray(R.array.provinces)

        with(binding) {

            // spinner
            val adapterCountry = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, countries)
            adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCountry.adapter = adapterCountry

            val adapterProvinces = ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_item, provinces)
            adapterProvinces.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerProvinces.adapter = adapterProvinces

            spinnerCountry.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
//                        val selectedItem = parent?.getItemAtPosition(position).toString()
                        Toast.makeText(this@MainActivity, countries[position], Toast.LENGTH_SHORT).show()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // no action
                    }
                }

            // date picker
            datePicker.init(
                datePicker.year,
                datePicker.month,
                datePicker.dayOfMonth
            ) {
                _, year, monthOfYear, dayOfMonth ->
                var selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                Toast.makeText(this@MainActivity, selectedDate, Toast.LENGTH_SHORT).show()

            }

            // Time Picker
            timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                Toast.makeText(this@MainActivity, selectedTime, Toast.LENGTH_SHORT).show()
            }

            // date picker if button click
            btnShowCalendar.setOnClickListener {
                val datePicker = DatePicker()
                datePicker.show(supportFragmentManager, "datePicker")
            }

            // Time picker if button click
            btnShowTimePicker.setOnClickListener {
                val timePicker = TimePicker()
                timePicker.show(supportFragmentManager, "timePicker")
            }

            btnShowAlertDialog.setOnClickListener {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Keluar")

                builder.setMessage("Apakah Anda yakin ingin keluar dari aplikasi?")
                builder.setPositiveButton("Ya") { dialog, which ->
                //lakukan sesuatu ketika tombol positif diklik
                    finish()
                }
                builder.setNegativeButton("Tidak") { dialog, _ ->
                //lakukan sesuatu ketika tombol negatif diklik
                    dialog.dismiss()
                }
                // Membuat dan menampilkan dialog
                val dialog = builder.create()
                dialog.show()
            }

            // dialog custom
            btnShowCustomDialog.setOnClickListener {
                val dialog = DialogExit()
                dialog.show(supportFragmentManager, "dialogExit")
            }


        }
    }

    override fun onDateSet(
        view: android.widget.DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        val selectedDate = "$year/${month + 1}/$dayOfMonth"
        Toast.makeText(this@MainActivity, selectedDate,
            Toast.LENGTH_SHORT).show()
    }

    override fun onTimeSet(view: android.widget.TimePicker?, hourOfDay: Int, minute: Int) {

        val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
        Toast.makeText(this@MainActivity, selectedTime, Toast.LENGTH_SHORT).show()
    }
}

// date picker dialog
class DatePicker: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val monthOfYear = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireActivity(),
            activity as DatePickerDialog.OnDateSetListener,
            year,
            monthOfYear,
            dayOfMonth
        )
    }
}

// Time Picker Dialog
class TimePicker: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(
            requireActivity(),
            activity as TimePickerDialog.OnTimeSetListener,
            hourOfDay,
            minute,
            DateFormat.is24HourFormat(activity)
        )
    }
}

// Dialog Exit (Custom dialog)

class DialogExit : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val binding = DialogExitBinding.inflate(inflater)
        with(binding){
            btnYes.setOnClickListener {
                requireActivity().finish()
            }
            btnNo.setOnClickListener {
                dismiss()
            }
        }
        builder.setView(binding.root)
        return builder.create()
    }
}