package com.example.primefitness.sign_in

import android.content.Context
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GoogleAuthClient(
    private val context : Context,
    private val oneTapClient :SignInClient
) {

    private  val auth = Firebase.auth

  //  suspend fun signIn(): IntentSender? {

//        val result = try {
//            oneTapClient.beginSignIn()
//
//        } catch (e: Exception) {
//        }

 //   }

}