package com.example.phase12

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {
        private lateinit var home: Button

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.login)
                home = findViewById<Button>(R.id.button14)
                home.setOnClickListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                }
        }
}