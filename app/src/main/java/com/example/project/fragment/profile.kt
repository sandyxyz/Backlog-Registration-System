package com.example.project.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.project.R
import com.example.project.databinding.FragmentProfileBinding
import java.util.*


class profile : Fragment() {

    lateinit var binding : FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         loaddata()
        binding.profiledob.setOnClickListener{
            val myChal  = Calendar.getInstance()
            val year = myChal.get(Calendar.YEAR)
            val month = myChal.get(Calendar.MONTH)
            val day = myChal.get(Calendar.DAY_OF_MONTH)
            val ddd =  DatePickerDialog(requireContext(),
                {
                        _, year, month, dayOfMonth ->
                    val selecteddate = "$dayOfMonth/${month + 1}/$year"
                    binding.profiledob.setText(selecteddate)


                },
                year,
                month,
                day
            )

            ddd.show()

        }
        binding.edit.setOnClickListener {
            binding.profilename.setText("")
            binding.profiledob.setText("")
            binding.profileemail.setText("")
            binding.profilepincode.setText("")
            binding.profilestate1.setText("")
            binding.profileaddress.setText("")

        }
        binding.save.setOnClickListener {
            savedata()

            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        }
    }
    private fun savedata() {

        val pn = binding.profilename.text.toString()
        val pd = binding.profiledob.text.toString()
        val pe = binding.profileemail.text.toString()
        val pp = binding.profilepincode.text.toString()
        val ps = binding.profilestate1.text.toString()
        val pa = binding.profileaddress.text.toString()


        if (pn.isEmpty() || pd.isEmpty() || pe.isEmpty() || pp.isEmpty() || ps.isEmpty() || pa.isEmpty()) {
            Toast.makeText(requireContext(), "please fill data", Toast.LENGTH_SHORT).show()
        } else {

            val sharedp: SharedPreferences? = this.activity?.getSharedPreferences("hello", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor? = sharedp?.edit()

//
            editor?.apply {

                putString("key1", pn)
                putString("key2", pd)
                putString("key3", pe)
                putString("key4", pp)
                putString("key5", ps)
                putString("key6", pa)


            }?.apply()

        }

    }

    private fun loaddata() {
        val sharedp: SharedPreferences? = this.activity?.getSharedPreferences("hello", Context.MODE_PRIVATE)
        val name: String? = sharedp?.getString("key1", "")
        val dob: String? = sharedp?.getString("key2","")
        val email: String? = sharedp?.getString("key3","")
        val pincode: String? = sharedp?.getString("key4","")
        val state: String? = sharedp?.getString("key5","")
        val address: String? = sharedp?.getString("key6","")

        binding.profilename.setText(name)
        binding.profiledob.setText(dob)
        binding.profileemail.setText(email)
        binding.profilepincode.setText(pincode)
        binding.profilestate1.setText(state)
        binding.profileaddress.setText(address)




    }


}