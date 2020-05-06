package com.example.vumlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

//val firestoreHelper: FirestoreHelper = FirestoreHelper()



class FirestoreHelperLab : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore_helper_lab)

        val userModel : Any? = FirestoreHelper().instantiateUser("WT27")
        Log.d("Debug", "User fetched: ${userModel.toString()}")


    }
}
