package com.pcm.gamzigamzi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Repo {
    fun getData(): LiveData<MutableList<Sensor>> {
        val mutableData = MutableLiveData<MutableList<Sensor>>()
        val database = Firebase.database
        val user = Firebase.auth.currentUser
        val userid =user?.uid
        val myRef = database.getReference("userList").child(userid.toString()).child("sensorList")

        myRef.addValueEventListener(object : ValueEventListener {
            val LIST_DATA: MutableList<Sensor> = mutableListOf<Sensor>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val getData = userSnapshot.getValue(Sensor::class.java)
                        LIST_DATA.add(getData!!)

                        mutableData.value = LIST_DATA
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        return mutableData
    }
}