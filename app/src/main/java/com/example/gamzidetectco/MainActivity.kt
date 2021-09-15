package com.example.gamzidetectco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamzidetectco.databinding.ActivityLoginBinding
import com.example.gamzidetectco.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //저장했던 uid와 token을 이용하여 서버에 보내준다.
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("userList")

        val token = MyApplication.prefs.getString("token","")
        val userid = MyApplication.prefs.getString("uid", "")
        myRef.child(userid).child("token").setValue(token)
    }
}