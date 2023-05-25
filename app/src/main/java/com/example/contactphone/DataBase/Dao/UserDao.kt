package com.example.contactphone.DataBase.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.contactphone.DataBase.Entity.User

@Dao
interface UserDao {
    @Query("select * from User")
    fun getAllUsers(): MutableList<User>

    @Insert
    fun addUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun update(user: User)



    @Query("select * from User where name = :ismi AND surname = :familiyasi")
    fun findUser(ismi: String, familiyasi:String): User
}