package com.example.gamzidetectco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamzidetectco.databinding.ActivityLoginBinding
import com.example.gamzidetectco.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerText.text="감지감지"

        //저장했던 uid와 token을 서버에 보내준다.
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("sensorList")
        // 전에 저장한 rasid를 가져온다
        val rid = MyApplication.prefs.getString("rasid","")

        //전에 저장한 토큰을 가져온다.
        val token = MyApplication.prefs.getString("token","")
        //토큰을 센서에 보내준다.
        myRef.child(rid).child("token").setValue(token)

        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var ppm = snapshot.child(rid).child("ppm").getValue()
                binding.tvPpmValue.text = ppm.toString()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}