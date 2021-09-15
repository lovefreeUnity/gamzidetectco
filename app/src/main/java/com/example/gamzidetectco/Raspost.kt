package com.example.gamzidetectco

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Raspost(
    var id: String? = "",
    var ppm: String? = "",
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "ppm" to ppm
        )
    }
}
