package com.example.contactphone

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.contactphone.DataBase.AppDataBase
import com.example.contactphone.DataBase.Entity.User

class ContactAdapter(var appDataBase: AppDataBase, val onpressed: onPressed): RecyclerView.Adapter<ContactAdapter.MyHolder>() {
    val list = appDataBase.getContactDao().getAllUsers()
    class MyHolder(view: View): RecyclerView.ViewHolder(view) {
        val FISH = view.findViewById<TextView>(R.id.fullname)
        val nomeri = view.findViewById<TextView>(R.id.number)
        val vieww = view.findViewById<ConstraintLayout>(R.id.item_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val myholder = MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false))
        return myholder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val a = list[position]
        holder.FISH.text = a.name +" "+ a.surname
        holder.nomeri.text = a.phoneNumber

        holder.vieww.setOnClickListener {
            onpressed.SetOnClick(a)
        }
    }

    interface onPressed{
        fun SetOnClick(user: User)
    }
}