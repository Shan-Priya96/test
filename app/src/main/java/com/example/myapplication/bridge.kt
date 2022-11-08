package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class bridge : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge)
        val btn=findViewById<Button>(R.id.addItems)
        btn.setOnClickListener{
            val intent = Intent(this, addItem:: class.java)
            startActivity(intent)
        }
    }
}