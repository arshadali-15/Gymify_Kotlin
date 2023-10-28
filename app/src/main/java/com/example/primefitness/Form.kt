package com.example.primefitness

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.util.Calendar
import java.util.Date

class Form : AppCompatActivity() {

    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val calendar = Calendar.getInstance()

    lateinit var nameET: EditText
    lateinit var phoneET: EditText
    lateinit var ageET :EditText
    lateinit var addressET: EditText
    lateinit var startDateET: EditText
    lateinit var endDateET: EditText
    lateinit var durationET: EditText
    lateinit var selectedGender: String
    lateinit var progressBar : ProgressBar



    lateinit var startDate :String
    lateinit var endDate :String
    var duration:Int=0
    lateinit var gymifyBtn :Button



    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)


        // OTHER VARIABLES-------------------------------------------------------------------------
        nameET=findViewById<EditText>(R.id.edit_name)
        phoneET=findViewById<EditText>(R.id.edit_phone)
        ageET=findViewById<EditText>(R.id.edit_age)
        addressET=findViewById<EditText>(R.id.edit_address)

        progressBar = findViewById<ProgressBar>(R.id.progress_circular)

        gymifyBtn=findViewById<Button>(R.id.gymify_btn)

        //-------------------------------------------------------------------------------------------


        // DATES MANAGER---------------------------------------------------------------------------

        startDateET = findViewById<EditText>(R.id.edit_startDate)
        endDateET = findViewById<EditText>(R.id.edit_endDate)
        durationET = findViewById<EditText>(R.id.edit_duration)

        startDate = startDateET.text.toString()



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

        duration = durationET.text.toString().toInt()

        val currentDate = getCurrentDate()
        startDateET.setText(currentDate)

        calendar.add(Calendar.MONTH, duration)

        endDate = dateFormat.format(calendar.time)



        endDateET.setText(endDate)

        startDateET.setOnClickListener {
            showDatePickerDialog()
        }
        endDateET.setOnClickListener {
            showDatePickerDialog()
        }
//--------------------------------------------------------------------------------------------
        // Submit BUTTON CLICK-------------------------------------------------------------------------------

        gymifyBtn.setOnClickListener {
            progressBar.visibility =View.VISIBLE
            gymify()
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

 //  ADDING DATA INTO THE FIRESTORE -------------------------------------------------------------------------------------------


    // Gymify BUTTON CLICK-------------------------------------------------------------------------------
    private fun gymify() {

        val  name = nameET.text.toString()
        val phone = phoneET.text.toString()
         val age= ageET.text.toString()
         val address= addressET.text.toString()
        val startDate = startDateET.text.toString()
        val endDate = endDateET.text.toString()
        val duration = durationET.text.toString()

        val memberMap = hashMapOf(
            "name" to name,
            "gender" to selectedGender,
            "phone" to phone,
            "age" to age,
            "address" to address,
            "startDate" to startDate,
            "endDate" to endDate,
            "duration" to duration
        )

        val userId  = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("users").document().set(memberMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Member Added Successfully", Toast.LENGTH_SHORT).show()
                nameET.text.clear()
                addressET.text.clear()
                ageET.text.clear()
                phoneET.text.clear()
                progressBar.visibility = View.INVISIBLE
            }
            .addOnFailureListener {
                Toast.makeText(this, "FAILURE!! Member Not Added", Toast.LENGTH_SHORT).show()

            }
    }

    // -------------------------------------------------------------------------------
}