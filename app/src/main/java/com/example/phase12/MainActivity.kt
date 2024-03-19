package com.example.phase12


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import com.example.phase12.databinding.ActivityMainBinding

import android.view.MenuItem
import android.widget.Toast
import android.view.View

import android.widget.PopupMenu
import androidx.viewbinding.ViewBinding


class MainActivity : BaseLayout() {
    private lateinit var binding: ViewBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar((binding as ActivityMainBinding).toolbar)



    }
}
