package com.example.gamzidetectco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamzidetectco.databinding.ActivityRasInformationBinding

class RasInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRasInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ras_information)

        binding = ActivityRasInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutHeader.headerText.text = "센서 정보"

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this,AddRasActivity::class.java)
            startActivity(intent)
        }

        val RasList = arrayListOf(
            Rasfile("박준혁","준혁 방"),
            Rasfile("박준혁","준혁 방"),
            Rasfile("박준혁","준혁 방"),
            Rasfile("박준혁","준혁 방"),
            Rasfile("박준혁","준혁 방"),
            Rasfile("박준혁","준혁 방"),
            Rasfile("박준혁","준혁 방"),
            Rasfile("박준혁","준혁 방"),
            Rasfile("박준혁","준혁 방"),
            Rasfile("박준혁","준혁 방"),
            Rasfile("박준혁","준혁 방")
        )

        binding.rvImformation.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        binding.rvImformation.setHasFixedSize(true)

        binding.rvImformation.adapter = RasAdapter(RasList)
    }
}