package com.example.primefitness

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import java.util.Calendar
import java.util.Date

class Form : AppCompatActivity() {

    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val calendar = Calendar.getInstance()

    lateinit var name: EditText
    lateinit var phone: EditText
    lateinit var address: EditText
    lateinit var startDateET: EditText
    lateinit var endDateET: EditText
    lateinit var durationET: EditText
    lateinit var selectedGender: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)


        // OTHER VARIABLES-------------------------------------------------------------------------

        name.findViewById<EditText>(R.id.edit_name).text.toString()
        phone.findViewById<EditText>(R.id.edit_phone).text.toString()
        address.findViewById<EditText>(R.id.edit_address).text.toString()

        //-------------------------------------------------------------------------------------------


        // DATES MANAGER---------------------------------------------------------------------------

        startDateET = findViewById<EditText>(R.id.edit_startDate)
        endDateET = findViewById<EditText>(R.id.edit_endDate)
        durationET = findViewById<EditText>(R.id.edit_duration)

        durationET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed, but required by the interface
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed, but required by the interface
            }

            override fun afterTextChanged(s: Editable?) {
                updateEndDate()

            }
        })

        val duration = durationET.text.toString().toInt()

        val currentDate = getCurrentDate()
        startDateET.setText(currentDate)

        calendar.add(Calendar.MONTH, duration)

        val endDate = dateFormat.format(calendar.time)

        endDateET.setText(endDate)

        startDateET.setOnClickListener {
            showDatePickerDialog()
        }
        endDateET.setOnClickListener {
            showDatePickerDialog()
        }
//--------------------------------------------------------------------------------------------


    }

    // DATES MANAGER-------------------------------------------------------------------------------


    private fun getCurrentDate(): String {
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                // Update the EditText with the selected date
                val selectedDate =
                    SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }.time)
                startDateET.setText(selectedDate)
            }, year, month, day
        )

        datePickerDialog.show()
    }

    private fun updateEndDate() {
        val duration = try {
            durationET.text.toString().toInt()
        } catch (e: NumberFormatException) {
            // Handle invalid input, e.g., show an error message
            return
        }

        calendar.time = dateFormat.parse(startDateET.text.toString()) ?: Date()

        calendar.add(Calendar.MONTH, duration)
        val endDate = dateFormat.format(calendar.time)
        endDateET.setText(endDate)
    }
    // ----------------------------------------------------------------------------------------------


    //  RADIO BUTTON HANDLER-------------------------------------------------------------------


    fun radioButtonhandler(view: View) {
        if (view is RadioButton) {
            selectedGender = when (view.id) {
                R.id.male_radio -> "Male"
                R.id.female_radio -> "Female"
                else -> ""
            }
        }
    }
//-------------------------------------------------------------------------------------------


}