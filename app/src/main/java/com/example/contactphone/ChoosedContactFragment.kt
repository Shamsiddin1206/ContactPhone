package com.example.contactphone

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.contactphone.DataBase.AppDataBase
import com.example.contactphone.DataBase.Entity.User
import com.example.contactphone.databinding.FragmentChoosedContactBinding

private const val ARG_PARAM1 = "param1"

class ChoosedContactFragment : Fragment() {
    private var param1: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as User
        }
    }

    private lateinit var binding: FragmentChoosedContactBinding
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        checkPermission()
        val appDataBase = AppDataBase.getInstance(requireContext())
        val contactList = appDataBase.getContactDao().getAllUsers()
        binding = FragmentChoosedContactBinding.inflate(inflater, container, false)
        binding.personName.text = param1!!.name +" "+param1!!.surname
        binding.personNumber.text = param1!!.phoneNumber
        val s = param1!!
        binding.delete.setOnClickListener {
            appDataBase.getContactDao().deleteUser(s)
            parentFragmentManager.beginTransaction().replace(R.id.main, MainFragment()).commit()
        }
        binding.personPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL);
            intent.data = Uri.parse("tel: ${"+998"+param1!!.phoneNumber}")
            startActivity(intent)
        }
        binding.edit.setOnClickListener {
            binding.edit.visibility = View.GONE
            binding.editDetails.visibility = View.VISIBLE
            binding.commit2.visibility = View.VISIBLE
            binding.name2.setText(param1!!.name)
            binding.surname2.setText(param1!!.surname)
            binding.phoneNumber2.setText(param1!!.phoneNumber)
        }
        binding.commit2.setOnClickListener {
            if (!binding.name2.text.isNullOrEmpty() && !binding.surname2.text.isNullOrEmpty() && !binding.phoneNumber2.text.isNullOrEmpty()){
                if (param1!!.phoneNumber != binding.phoneNumber2.text.toString()){
                    for (i in appDataBase.getContactDao().getAllUsers()){
                        if (i.phoneNumber == binding.phoneNumber2.text.toString()){
                            Toast.makeText(requireContext(), "Bunday nomer uje mavjud", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                    }
                    s.phoneNumber = binding.phoneNumber2.text.toString()
                    s.name = binding.name2.text.toString()
                    s.surname = binding.surname2.text.toString()
                    appDataBase.getContactDao().update(s)


                    binding.edit.visibility = View.VISIBLE
                    binding.editDetails.visibility = View.GONE
                    binding.commit2.visibility = View.GONE

                    binding.personName.text = s.name + " " + s.surname
                    binding.personNumber.text = s.phoneNumber
                }
            }
        }
        return binding.root
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE), 101)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: User) =
            ChoosedContactFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}