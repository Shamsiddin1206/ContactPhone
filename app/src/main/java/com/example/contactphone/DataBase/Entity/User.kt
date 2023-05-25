package com.example.contactphone.DataBase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var name:String,
    var surname:String,
    var phoneNumber:String
):java.io.Serializable