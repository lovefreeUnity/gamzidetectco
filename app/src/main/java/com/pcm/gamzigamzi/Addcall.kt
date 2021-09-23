package com.pcm.gamzigamzi

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Addcall(
    var name: String? = "",
    var number : String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "number" to number            )
    }
}