package com.example.contactphone.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactphone.DataBase.Dao.UserDao
import com.example.contactphone.DataBase.Entity.User


@Database(entities = [User::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getContactDao(): UserDao

    companion object{
        var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase{
            if (instance==null){
                instance = Room.databaseBuilder(context, AppDataBase::class.java, "app_db")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}