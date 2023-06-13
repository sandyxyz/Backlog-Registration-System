package com.example.project


import android.app.DatePickerDialog
import com.example.project.databinding.FragmentRegisterBinding

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.adapter.baclklogadapter
import com.example.project.data.yserdatra
import java.util.*
import kotlin.collections.ArrayList


class Register : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    lateinit var rv : RecyclerView
    lateinit var badapter : baclklogadapter
    lateinit var list : ArrayList<yserdatra>




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        rv = view.findViewById(R.id.backlogrecycler)
        rv.layoutManager = LinearLayoutManager(requireContext())
        list = arrayListOf()


        binding.addf.setOnClickListener{
            var d = Dialog(requireContext())
            var inflater = LayoutInflater.from(requireContext())
            val v = inflater.inflate(R.layout.backlogpopup, null)
            d.setContentView(v)
            var lbtn: Button = v.findViewById(R.id.save)
            var subject: EditText = v.findViewById(R.id.subjects)
            var dob: EditText = v.findViewById(R.id.dateofexam)
            dob.setOnClickListener {
                val myChal  = Calendar.getInstance()
        val year = myChal.get(Calendar.YEAR)
        val month = myChal.get(Calendar.MONTH)
        val day = myChal.get(Calendar.DAY_OF_MONTH)
        val ddd =  DatePickerDialog(requireContext(),
            {
                    _, year, month, dayOfMonth ->
                val selecteddate = "$dayOfMonth/${month + 1}/$year"
                dob.setText(selecteddate)


            },
            year,
            month,
            day
        )

        ddd.show()
            }
           lbtn.setOnClickListener {
               if (subject.text.isNotEmpty() && dob.text.isNotEmpty()) {
                   var s = subject.text.toString()
                   var d = dob.text.toString()

                   list.add(yserdatra(s, d))
                   badapter = baclklogadapter(requireContext() , list)
                   rv.adapter = badapter
               }else {
                   Toast.makeText(requireContext(), "please filld data", Toast.LENGTH_SHORT).show()
               }
               d.dismiss()
           }

          d.setCancelable(true)
           d.create()
          d.show()
        }

//        loaddata()





    }}


//        binding.profiledob.setOnClickListener {
//            calender()
//        }
//        binding.save.setOnClickListener {
//            savedata()
//            rdata()
//
//            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
//            val fm = this.activity?.supportFragmentManager
//            val ft = fm?.beginTransaction()
//            ft?.replace(R.id.mainframe , sfull())
//            ft?.commit()
//
//
//
//        }
//        binding.edit.setOnClickListener {
//            binding.profilename.setText("")
//            binding.profiledob.setText("")
//            binding.profileemail.setText("")
//            binding.pincode.setText("")
//
//            binding.profileaddress.setText("")
//
//        }



//    }

//    private fun savedata() {

//        val pn = binding.profilename.text.toString()
//        val pd = binding.profiledob.text.toString()
//        val pe = binding.profileemail.text.toString()
//        val pp = binding.pincode.text.toString()
//        val pa = binding.profileaddress.text.toString()

//
//        if (pn.isEmpty() || pd.isEmpty() || pe.isEmpty() || pp.isEmpty()  || pa.isEmpty()) {
//            Toast.makeText(requireContext(), "please fill data", Toast.LENGTH_SHORT).show()
//        } else {
//
//            val sharedp: SharedPreferences? = this.activity?.getSharedPreferences("hello", Context.MODE_PRIVATE)
//            val editor: SharedPreferences.Editor? = sharedp?.edit()
//
////
//            editor?.apply {
//
//                putString("key1", pn)
//                putString("key2", pd)
//                putString("key3", pe)
//                putString("key4", pp)
//
//                putString("key6", pa)
//
//
//            }?.apply()
//
//        }
//
//    }
//    private fun loaddata() {
//        val sharedp: SharedPreferences? = this.activity?.getSharedPreferences("hello", Context.MODE_PRIVATE)
//        val name: String? = sharedp?.getString("key1", "")
//        val dob: String? = sharedp?.getString("key2","")
//        val email: String? = sharedp?.getString("key3","")
//        val pincode: String? = sharedp?.getString("key4","")
//
//        val address: String? = sharedp?.getString("key6","")
//
//        binding.profilename.setText(name)
//        binding.profiledob.setText(dob)
//        binding.profileemail.setText(email)
//        binding.pincode.setText(pincode)
//
//        binding.profileaddress.setText(address)
//
//


//    }
//    private fun calender() {
//        val myChal  = Calendar.getInstance()
//        val year = myChal.get(Calendar.YEAR)
//        val month = myChal.get(Calendar.MONTH)
//        val day = myChal.get(Calendar.DAY_OF_MONTH)
//        val ddd =  DatePickerDialog(requireContext(),
//            {
//                    _, year, month, dayOfMonth ->
//                val selecteddate = "$dayOfMonth/${month + 1}/$year"
//                binding.profiledob.setText(selecteddate)
//
//
//            },
//            year,
//            month,
//            day
//        )
//
//        ddd.show()
//    }
//}