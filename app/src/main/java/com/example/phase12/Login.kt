package com.example.phase12

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.phase12.databinding.LoginBinding

class Login : BaseLayout() {
        private lateinit var binding: ViewBinding

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.login)

                binding = LoginBinding.inflate(layoutInflater)
                setContentView(binding.root)

                setSupportActionBar(findViewById(R.id.toolbar))
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
}