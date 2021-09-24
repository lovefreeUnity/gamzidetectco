package com.pcm.gamzigamzi.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.pcm.gamzigamzi.DataManager
import com.pcm.gamzigamzi.MyApplication
import com.pcm.gamzigamzi.R
import com.pcm.gamzigamzi.databinding.ActivityMenuBinding
import java.util.jar.Manifest

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    val mContext = this

    var auth : FirebaseAuth ?= null
    var googleSignInClient : GoogleSignInClient ?= null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.header.headertext.text = "감지감지"

        binding.layoutAdd.setOnClickListener {
            startActivity(Intent(this, AddRasActivity::class.java))
            this.finish()
        }

        binding.layoutList.setOnClickListener {
            startActivity(Intent(this, TextActivity::class.java))
            this.finish()
        }

        binding.layoutAddcall.setOnClickListener {
            startActivity(Intent(this, AddCallActivity::class.java))
            this.finish()
        }

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        var google_sign_out_button = findViewById<Button>(R.id.btn_logout)

        google_sign_out_button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            googleSignInClient?.signOut()
            MyApplication.prefs.setString("uid","")
            MyApplication.prefs.setString("name","")
            MyApplication.prefs.setString("num","")
            MyApplication.prefs.setString("rasid","")
            var logoutIntent = Intent (this, MainActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(logoutIntent)
        }

        val name = MyApplication.prefs.getString("name", "")
        val number = MyApplication.prefs.getString("num", "")

        if (name != null && number != null) {
            binding.tvNameValue.text = name
            binding.tvNumValue.text = number
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val Ref: DatabaseReference = database.getReference("userList")
            val uid = MyApplication.prefs.getString("uid","")
            Ref.child(uid).child("number").setValue(number)
        }
        checkPermission()
    }

    fun checkPermission(){
        val smsPemission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)
        if(smsPemission == PackageManager.PERMISSION_GRANTED){
            startProcess()
        }else{
            requestPermission()
        }
    }
    fun startProcess(){
        Toast.makeText(this,"sms를 권한 허용 완료",Toast.LENGTH_SHORT).show()
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS),99)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            99->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startProcess()
                }else{
                    Toast.makeText(this,"권한을 승인하지 않으면 앱이 종류됩니다.",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
//    private fun callfriend(){
//        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
//        val myRef: DatabaseReference = database.getReference("sensorList")
//
//        val rid = MyApplication.prefs.getString("rasid", "")
//
//        if(rid!=null){
//            myRef.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val ppm = snapshot.child(rid).child("ppm").getValue()
//                    if(ppm.toString().toInt()>=800){
//                        val input = MyApplication.prefs.getString("num", "")
//                        if(input != null){
//                            val permissionListener = object : PermissionListener {
//                                override fun onPermissionGranted() {
//                                    val myUri = Uri.parse("tel:${input}")
//                                    val myIntent = Intent(Intent.ACTION_CALL, myUri)
//                                    startActivity(myIntent)
//                                }
//
//                                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//                                }
//                            }
//                            TedPermission.with(mContext).setPermissionListener(permissionListener)
//                                .setDeniedMessage("[설정] 에서 권한을 열어줘야 전화 연결이 가능합니다.")
//                                .setPermissions(android.Manifest.permission.CALL_PHONE)
//                                .check()
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                }
//            })
//        }
//    }
}