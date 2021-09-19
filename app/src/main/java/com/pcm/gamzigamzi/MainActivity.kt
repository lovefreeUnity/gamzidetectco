package com.pcm.gamzigamzi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.pcm.gamzigamzi.databinding.ActivityMainBinding
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.headerText.text="현재 상황"
        binding.tvAddress.setText(DataManager.address)


        val now = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일",Locale.KOREA).format(now)
        binding.tvDate.text = simpleDateFormat



        //저장했던 uid와 token을 서버에 보내준다.
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("sensorList")

        // 전에 저장한 rasid를 가져온다
        val rid = DataManager.rasid


        //조건에 맞춰 배경 색깔 변경
        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val ppm = snapshot.child(rid).child("ppm").getValue()
                binding.tvPpmValue.setText(ppm.toString())

                if(ppm.toString().toInt()>=400){
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.danger))
                    binding.tvStatus.setText("심각")
                    binding.tvStatusLong.setText("일산화탄소 수치가 비정상적으로 높습니다.\n주변에 일산화탄소를 배출 하는 물질이 있는지 확인 하여 주시고\n 환기를 하여 주십시오")
                }
                else if(ppm.toString().toInt()>=200){
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.risk))
                    binding.tvStatus.setText("주의")
                    binding.tvStatusLong.setText("두통이나 매스꺼움 등을 겪을 수 있습니다. 주변을 환기를 시켜 일산화탄소를 내보내 주세요")
                }
                else if(ppm.toString().toInt()>=50){
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.caution))
                    binding.tvStatus.setText("관심")
                    binding.tvStatusLong.setText("2~3시간내에 두통이 찾아올 수 있습니다.창문을 열어 환기를 시켜주십시오.")
                }
                else{
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.nomal))
                    binding.tvStatus.setText("정상")
                    binding.tvStatusLong.setText("정상적인 일산화탄소 수치입니다.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


        nextPage()
    }
    fun nextPage(){
        binding.btnSensors.setOnClickListener {
            val intent = Intent(this, TextActivity::class.java)
            startActivity(intent)
        }
    }
}