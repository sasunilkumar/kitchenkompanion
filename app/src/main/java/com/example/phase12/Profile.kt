package com.example.phase12

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.ProfileBinding
import com.example.phase12.ui.theme.AppBar

class Profile : AppBar() {
    private lateinit var binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBar()
    }
}