package com.example.gamzidetectco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
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
        val rid = DataManager.rasid


        //조건에 맞춰 배경 색깔 변경
        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var ppm = snapshot.child(rid).child("ppm").getValue()
                binding.tvPpmValue.text = ppm.toString()
                if(ppm.toString().toInt()>=400){
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.danger))
                    binding.tvStatus.setText("위험")
                }
                else if(ppm.toString().toInt()>=200){
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.risk))
                }
                else if(ppm.toString().toInt()>=50){
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.caution))
                }
                else{
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.nomal))
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}