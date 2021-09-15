package com.example.gamzidetectco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.gamzidetectco.databinding.ActivityAddRasBinding
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class AddRasActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddRasBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddRasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHeader.setText("기기 등록")
        binding.btnSensor.setText("기기 등록")


        Click()

        }

    fun Click(){
        binding.btnSensor.setOnClickListener {
            val adrass = binding.RasAdrress.text.toString()
            val name = binding.RasName.text.toString()
            val rasId = binding.RasId.text.toString()
            writeNewPost(adrass,name ,rasId)

            val intent = Intent(this,RasInformationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun writeNewPost(adress: String, token: String, name: String) {
        val userid = MyApplication.prefs.getString("uid", "")

        val key = userid

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("userList")

        val post = Post(adress, token, name)
        val postValues = post.toMap()


        //유저 설정 저장
        val childUpdates = hashMapOf<String, Any>(
            "/$userid/sensorList/$key" to postValues
        )

        myRef.updateChildren(childUpdates)
    }

}
