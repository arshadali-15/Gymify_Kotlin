package com.example.primefitness

import android.text.Editable
import android.widget.EditText
import java.lang.reflect.Constructor

data class MemberModel(

    val name: String,
    val address :String,
    val age :String,
    val duration :String,
    val gender :String,
    val phone :String,

   )
{
   constructor(): this("", "","","","","",)
}
