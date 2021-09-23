package com.pcm.gamzigamzi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pcm.gamzigamzi.MyApplication
import com.pcm.gamzigamzi.R
import com.pcm.gamzigamzi.databinding.ActivityAddCallBinding


class AddCallActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCallBinding


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
            MyApplication.prefs.setString("callname",name.toString())
            MyApplication.prefs.setString("num",number.toString())

            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}