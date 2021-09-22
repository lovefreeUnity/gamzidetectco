package com.pcm.gamzigamzi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pcm.gamzigamzi.databinding.ActivityAddRasBinding
import com.google.firebase.database.*
import com.pcm.gamzigamzi.MyApplication
import com.pcm.gamzigamzi.Save

class AddRasActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddRasBinding

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddRasBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvHeader.setText("기기 정보 등록")
        binding.btnSensor.setText("기기 등록")

        //버튼 클릭시
        Click()
    }

    fun Click(){

        //가져올 데이터 위치 지정
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("sensorList")

        //값 가져오기
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 유저가 입력란에 입력한 값을 가져온다.
                val rasid = binding.edtRasid.text

                //이 값들을 을 서버에 보내준뒤 읽어 올것
                val rasadd = binding.edtAddress.text
                val rasname =binding.edtRasname.text

                binding.btnSensor.setOnClickListener {
                    //sensorList 안에 id 안에 유저가 입력한 id가 있는지 있다면 그것의 id를 가져와준다.
                    val Rasid = snapshot.child(rasid.toString()).child("id").getValue()

                    if (Rasid!=null){
                        //실제 있는 센서 id라면 그 센서의 이름을 저장
                        MyApplication.prefs.setString("rasid",Rasid.toString())

                        //그리고 토큰을 해당 센서에 보내준다.
                        val token = MyApplication.prefs.getString("token","")
                        myRef.child(Rasid.toString()).child("token").setValue(token)


                        //유저가 설정한 정보들 저장
                        writeNewPost(rasadd.toString(), rasid.toString(), rasname.toString())
                        //이동
                        nextpage()
                    }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

    }

    fun nextpage(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
    }

    private fun writeNewPost(adress: String, id: String, name: String) {
        val myRef : DatabaseReference = database.getReference("userList")
        val userid = MyApplication.prefs.getString("uid","")

        val key = MyApplication.prefs.getString("rasid","")

        val post = Save(adress, id, name)
        val postValues = post.toMap()

        //유저 설정 저장
        if(key!=null){
            val childUpdates = hashMapOf<String, Any>(
                "$userid/sensorList/$key" to postValues
            )
            myRef.updateChildren(childUpdates)
        }
    }

}
