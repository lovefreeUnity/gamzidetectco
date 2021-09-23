package com.pcm.gamzigamzi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import com.pcm.gamzigamzi.Addcall
import com.pcm.gamzigamzi.MyApplication
import com.pcm.gamzigamzi.R
import com.pcm.gamzigamzi.databinding.ActivityAddCallBinding

class AddCallActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCallBinding

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_call)

        binding = ActivityAddCallBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvHeader.setText("비상연락망")
        binding.btnSensor.setText("전화번호")

        val name=binding.edtName.text
        val number=binding.edtCall.text
        binding.btnSensor.setOnClickListener {
            addlist(name.toString(),number.toString())
        }
    }

    fun addlist(name: String, number: String){
        val myRef : DatabaseReference = database.getReference("userList")
        val userid = MyApplication.prefs.getString("uid","")

        val post = Addcall(name,number)
        val postValues = post.toMap()

        //유저 설정 저장
        val childUpdates = hashMapOf<String, Any>(
            "$userid/numberList/" to postValues
        )
        myRef.updateChildren(childUpdates)
    }
}