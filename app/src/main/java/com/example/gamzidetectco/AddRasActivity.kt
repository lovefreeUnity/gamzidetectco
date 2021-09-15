package com.example.gamzidetectco

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
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

    lateinit var rasList : ArrayList<Raspost>
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()

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

            //가져올 데이터 위치
            val database : FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef : DatabaseReference = database.getReference("sensorList")


            //값 변동시 가져오기
            myRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    //edt중 라즈베리파이 id입력란의 값을 가녀온다
                    val rasid = binding.RasId.text
                    //myRef = snapshot
                    val Rasid = snapshot.child(rasid.toString()).child("id").getValue()

                    if (Rasid.toString().equals(rasid.toString())){//해당 id가 있다면
                        //가져온값과 서버에있는 센서 id와 비교
                        val ppm = snapshot.child(rasid.toString()).child("ppm").getValue()
                        binding.tvHeader.text= Rasid.toString()

                        val adrass = binding.RasAdrress.text.toString()
                        val name = binding.RasName.text.toString()
                        val rasId = binding.RasId.text.toString()
                        writeNewPost(adrass,rasId,name)

                        val intent = Intent(this@AddRasActivity,RasInformationActivity::class.java)
                        startActivity(intent)
                    }

                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    private fun writeNewPost(adress: String, token: String, name: String) {
        val myRef : DatabaseReference = database.getReference("userList")

        val userid = MyApplication.prefs.getString("uid", "")

        val key = userid

        val post = Post(adress, token, name)
        val postValues = post.toMap()

        //유저 설정 저장
        val childUpdates = hashMapOf<String, Any>(
            "/$userid/sensorList/$key" to postValues
        )

        myRef.updateChildren(childUpdates)
    }

}
