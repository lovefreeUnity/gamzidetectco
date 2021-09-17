package com.example.gamzidetectco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gamzidetectco.databinding.ActivityAddRasBinding
import com.example.gamzidetectco.databinding.ActivityReaddataBinding
import com.google.firebase.database.*

class Readdata : AppCompatActivity() {

    private lateinit var binding: ActivityReaddataBinding

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_readdata)

        binding = ActivityReaddataBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}