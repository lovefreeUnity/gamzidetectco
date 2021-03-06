package com.pcm.gamzigamzi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.pcm.gamzigamzi.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.pcm.gamzigamzi.DataManager
import com.pcm.gamzigamzi.MyApplication
import com.pcm.gamzigamzi.R
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //저장했던 uid와 서버에 보내준다.
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("sensorList")

        // 전에 저장한 rasid를 가져온다
        val rid = DataManager.rasid
        val address= DataManager.address
        binding.tvAddress.text =address
        binding.headerText.text="현재 ppm수치"

        val now = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일",Locale.KOREA).format(now)
        binding.tvDate.text = simpleDateFormat

//        val check =MyApplication.prefs.getString("check","")
//        checkPermission()

        //조건에 맞춰 배경 색깔 변경
        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val ppm = snapshot.child(rid).child("ppm").getValue()
                binding.tvPpmValue.setText(ppm.toString())
//                if(check.equals("1")){
//                    if(ppm.toString().toInt()>=800){
//                        val number = MyApplication.prefs.getString("num", "")
//                        val myUri = Uri.parse("tel:${number}")
//                        val myIntent = Intent(Intent.ACTION_CALL, myUri)
//                        startActivity(myIntent)
//
//                    }
//                }
                if(ppm.toString().toInt()>=3200){
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,
                        R.color.emergency
                    ))
                    binding.tvStatus.setText("위급상황")
                    binding.tvStatusLong.setText("5 ~ 10분 내 두통, 매스꺼움\n" + "30분 뒤 사망")
                }
                else if(ppm.toString().toInt()>=1600){
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,
                        R.color.danger
                    ))
                    binding.tvStatus.setText("위험")
                    binding.tvStatusLong.setText("2시간이 지나면 사망")
                }
                else if(ppm.toString().toInt()>=800){
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,
                        R.color.caution2
                    ))
                    binding.tvStatus.setText("조금 위험")
                    binding.tvStatusLong.setText("45분에 두통, 매스꺼움, 구토\n" + "2시간 내 실신")
                }
                else if(ppm.toString().toInt()>=400){
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,
                        R.color.caution1
                    ))
                    binding.tvStatus.setText("관심")
                    binding.tvStatusLong.setText("1 ~ 2 시간에 전두통\n" + "2.5 ~ 3시간 안에 후두통")
                }
                else if(ppm.toString().toInt()>=200){
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,
                        R.color.caution1
                    ))
                    binding.tvStatus.setText("관심")
                    binding.tvStatusLong.setText("2 ~ 3시간 내 가벼운 두통")
                }
                else{
                    binding.root.setBackgroundColor(ContextCompat.getColor(this@MainActivity,
                        R.color.nomal
                    ))
                    binding.tvStatus.setText("정상")
                    binding.tvStatusLong.setText("정상적인 일산화탄소 수치입니다.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

//    fun checkPermission(){
//        val smsPemission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)
//        if(smsPemission == PackageManager.PERMISSION_GRANTED){
//            startProcess()
//        }else{
//            requestPermission()
//        }
////    }
//    fun startProcess(){
//        Toast.makeText(this,"전화걸기 허용 완료",Toast.LENGTH_SHORT).show()
//        MyApplication.prefs.setString("check","1")
//    }
//
//    fun requestPermission(){
//        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE),98)
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        when(requestCode){
//            98->{
//                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                    startProcess()
//                }else{
//                    Toast.makeText(this,"권한을 승인하지 않으면 자동신고기능을 사용할 수 없습니다..",Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    override fun onBackPressed() {
        val intent = Intent(this, TextActivity::class.java)
        startActivity(intent)
        finish()
    }

}