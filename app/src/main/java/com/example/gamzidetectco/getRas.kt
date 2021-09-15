package com.example.gamzidetectco

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

open class getRas{
    var id: String? = ""
    var ppm: String? = ""

    constructor(_id: String,_ppm: String){
        id = _id
        ppm=_ppm
    }
}
