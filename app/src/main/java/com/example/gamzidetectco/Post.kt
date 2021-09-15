package com.example.gamzidetectco

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post(
    var adress: String? = "",
    var token: String? = "",
    var name : String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "adress" to adress,
            "token" to token,
            "name" to name
        )
    }
}
