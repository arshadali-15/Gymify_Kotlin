package com.example.primefitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    lateinit var emailET : EditText
    lateinit var passwordET : EditText
    lateinit var loginBtn : Button
    lateinit var googleLoginBtn  :Button
    lateinit var registerLink : TextView

    lateinit var sEmail : String
    lateinit var sPassword : String

    lateinit var auth :FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        // GOOGLE SIGNIN --------------------------------------------------------

        val currentUser = auth.currentUser

        if (currentUser != null) {
            // The user is already signed in, navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
        }
        googleLoginBtn = findViewById(R.id.googleLogin_btn)

        googleLoginBtn.setOnClickListener{

          signIn()
        }

       //----------------------------------------------------------------





        val name =auth.currentUser?.displayName.toString()
        val mail =auth.currentUser?.displayName.toString()
        val phone =auth.currentUser?.displayName.toString()

        emailET = findViewById<EditText>(R.id.et_mail)
        passwordET = findViewById<EditText>(R.id.et_password)
        loginBtn = findViewById<Button>(R.id.login_btn)
        registerLink = findViewById(R.id.reg_link)

//  REGISTER USER -----------------------------------------------------------------------------------------------
        registerLink.setOnClickListener {

            registerUser()
        }
//   -----------------------------------------------------------------------------------------------


        //  LOGIN USER -----------------------------------------------------------------------------------------------

        loginBtn.setOnClickListener{

           loginUser()
        }
    }
    //-----------------------------------------------------------------------------------------------


    // GOOGLE SIGNIN --------------------------------------------------------


    private fun signIn() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webClientId))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
    // --------------------------------------------------------------------------------------------


    private fun provideAccess(user:FirebaseUser){
        val intent = Intent(this,MainActivity::class.java)
         startActivity(intent)

    }


    private  fun registerUser(){
        sEmail = emailET.text.toString().trim()
        sPassword = passwordET.text.toString().trim()

        if (sEmail.isEmpty() || sPassword.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
            return
        }



        auth.createUserWithEmailAndPassword(sEmail, sPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    val user = auth.currentUser
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    // You can navigate to the next screen or perform other actions here.
                } else {
                    // Registration failed
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun loginUser(){
        sEmail = emailET.text.toString().trim()
        sPassword = passwordET.text.toString().trim()

        auth.signInWithEmailAndPassword(sEmail, sPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful
                    val user = auth.currentUser
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    // You can navigate to the next screen or perform other actions here.
                } else {
                    // Login failed
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}