package com.pcm.gamzigamzi

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Save(
    var address: String? = "",
    var id: String? = "",
    var name : String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "address" to address,
            "id" to id,
            "name" to name
        )
    }
}
