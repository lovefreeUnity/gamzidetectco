package com.example.gamzidetectco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamzidetectco.databinding.ActivityAddRasBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddRasActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddRasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ras)
        binding = ActivityAddRasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.layoutSensor.tvHeader.setText("기기 등록")
        binding.layoutSensor.btnSensor.setText("기기 등록")

        binding.layoutSensor.btnSensor.setOnClickListener {

            val urasId:String = binding.layoutSensor.RasId.text.toString()

            if(urasId != null){
                val database : FirebaseDatabase = FirebaseDatabase.getInstance()
                val RasRef : DatabaseReference = database.getReference("sensorlist").child(urasId)
                if(RasRef.child("id").get().equals(urasId)==false){

                }
                
            }

            val intent = Intent(this,RasInformationActivity::class.java)
            startActivity(intent)
        }
    }
}
