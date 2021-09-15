package com.example.gamzidetectco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gamzidetectco.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    var googleSiginClient : GoogleSignInClient? = null
    val RC_SIGN_IN = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSiginClient = GoogleSignIn.getClient(this, gso)

        binding.btnGoogle.setOnClickListener {
            var signinIntent = googleSiginClient?.signInIntent
            startActivityForResult(signinIntent, RC_SIGN_IN)
        }
    }
    public override fun onStart(){
        super.onStart()
        Nextpage()

    }

    fun Nextpage(){
        var currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser!=null){
            startActivity(Intent(this,AddRasActivity::class.java))
            this.finish()
        }
    }

    fun FirebaseAuthWithGoogle(acct: GoogleSignInAccount?){
        val credential = GoogleAuthProvider.getCredential(acct?.idToken,null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{ task->
            if (task.isSuccessful){

                //uid저장
                val user = Firebase.auth.currentUser
                val userid =user?.uid

                MyApplication.prefs.setString("uid",userid.toString())

                //유저 db생성
                val database : FirebaseDatabase = FirebaseDatabase.getInstance()
                val myRef : DatabaseReference = database.getReference("userList")

                myRef.child(userid.toString()).child("userId").setValue(userid.toString())

                Nextpage()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)
                FirebaseAuthWithGoogle(account)
            }
            catch (e: ApiException){
                Toast.makeText(this,"로그인실패", Toast.LENGTH_SHORT).show()
            }
        }
        else{

        }
    }
}