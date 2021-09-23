package com.pcm.gamzigamzi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.pcm.gamzigamzi.databinding.ActivityLoginBinding
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
import com.pcm.gamzigamzi.MyApplication
import com.pcm.gamzigamzi.R

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    var googleSiginClient : GoogleSignInClient? = null
    val RC_SIGN_IN = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutheader.headertext.setText("로그인")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSiginClient = GoogleSignIn.getClient(this, gso)

        binding.btnGoogle.setOnClickListener {
            val signinIntent = googleSiginClient?.signInIntent
            startActivityForResult(signinIntent, RC_SIGN_IN)
        }
    }
    public override fun onStart(){
        super.onStart()
        val user = Firebase.auth.currentUser
        val userid =user?.uid
        MyApplication.prefs.setString("uid",userid.toString())
        if(userid!=null){
            Nextpage()
        }
        else{
            Toast.makeText(this,"구글 로그인이 되어있지 않습니다.",Toast.LENGTH_SHORT).show()
        }
    }

    fun Nextpage(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser!=null){
            startActivity(Intent(this, MenuActivity::class.java))
            this.finish()
        }
    }

    fun FirebaseAuthWithGoogle(acct: GoogleSignInAccount?){
        val credential = GoogleAuthProvider.getCredential(acct?.idToken,null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{ task->
            if (task.isSuccessful){

                //uid불러오기
                val user = Firebase.auth.currentUser
                val userid =user?.uid

                if(userid != null){

                    MyApplication.prefs.setString("uid",userid.toString())
                    //유저 db생성
                    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
                    val myRef : DatabaseReference = database.getReference("userList")
                    myRef.child(userid.toString()).child("userId").setValue(userid.toString())
                    Nextpage()
                }
                else{
                    Toast.makeText(this,"오류가 발생하였습니다.",Toast.LENGTH_SHORT).show()
                }
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