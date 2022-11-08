package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn=findViewById<Button>(R.id.button_send)
        btn.setOnClickListener{
            val intent = Intent(this, SignUpActivity:: class.java)
            startActivity(intent)
        }
         auth =FirebaseAuth.getInstance()
        if (auth.currentUser == null)
        {
            val intent=Intent(this, SignInActivity:: class.java)
            startActivity(intent)
        }
    }
}