package com.pcm.gamzigamzi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.pcm.gamzigamzi.MyApplication
import com.pcm.gamzigamzi.R
import com.pcm.gamzigamzi.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

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

            var logoutIntent = Intent (this, MainActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(logoutIntent)
        }

        val name = MyApplication.prefs.getString("name", "")
        val number = MyApplication.prefs.getString("num", "")

        if (name != null && number != null) {
            binding.tvNameValue.text = name
            binding.tvNumValue.text = number
        }
    }
}