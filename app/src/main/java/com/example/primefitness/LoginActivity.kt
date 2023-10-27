package com.example.primefitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User

class LoginActivity : AppCompatActivity() {

    lateinit var emailET : EditText
    lateinit var passwordET : EditText
    lateinit var loginBtn : Button
    lateinit var registerBtn : Button

    lateinit var sEmail : String
    lateinit var sPassword : String

    lateinit var auth :FirebaseAuth

    //lateinit var user :FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        val name =auth.currentUser?.displayName.toString()
        val mail =auth.currentUser?.displayName.toString()
        val phone =auth.currentUser?.displayName.toString()

        emailET = findViewById<EditText>(R.id.et_mail)
        passwordET = findViewById<EditText>(R.id.et_password)
        loginBtn = findViewById<Button>(R.id.login_btn)
        registerBtn = findViewById<Button>(R.id.register_btn)


        registerBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        loginBtn.setOnClickListener{

            sEmail = emailET.text.toString().trim()
            sPassword = passwordET.text.toString().trim()

            auth.createUserWithEmailAndPassword(sEmail,sPassword)
                .addOnCompleteListener(this){
                    task ->
                    if(task.isSuccessful){
                       val user = auth.currentUser
                        if (user != null) {
                            provideAccess(user)
                        }
                    }else{
                        Toast.makeText(this , "Login Failed \n Wrong Email or Password" ,Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun provideAccess(user:FirebaseUser){
        val intent = Intent(this,MainActivity::class.java)
         startActivity(intent)

    }
}