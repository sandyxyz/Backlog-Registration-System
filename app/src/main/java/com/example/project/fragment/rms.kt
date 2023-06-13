package com.example.project.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.project.MainActivity
import com.example.project.R


class rms : Fragment() {

   lateinit var et : EditText
   lateinit var tv : TextView
   lateinit var s1v : String
   lateinit var s2v : String
   lateinit var s3v : String
    val CHANNEL_ID = "my_channel_id"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v =  inflater.inflate(R.layout.fragment_rms, container, false)

        val spinner1 = v.findViewById<Spinner>(R.id.s1)
        val spinner2 = v.findViewById<Spinner>(R.id.s2)
        val spinner3 = v.findViewById<Spinner>(R.id.s3)
        val btn = v.findViewById<CardView>(R.id.save)
        et = v.findViewById(R.id.getfeedback)
        tv = v.findViewById(R.id.texttt)

        val spinner1Adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner, android.R.layout.simple_spinner_item)
        spinner1.adapter = spinner1Adapter

        val spinner2Adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner2, android.R.layout.simple_spinner_item)
        spinner2.adapter = spinner2Adapter

        val spinner3Adapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner3, android.R.layout.simple_spinner_item)
        spinner3.adapter = spinner3Adapter


        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                s1v = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                s2v = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                s3v = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        btn.setOnClickListener{
            if(s1v.isNotEmpty() && s2v.isNotEmpty() && s3v.isNotEmpty()){
                if(tv.text.toString() == "Next"){
                    et.visibility = View.VISIBLE
                    tv.setText("Save")
                }else {
                    if(et.text.toString().isNotEmpty()) {
                        et.visibility = View.GONE
                        tv.setText("Next")
                        notification()

                    } else {
                        Toast.makeText(requireContext(), "Please write your query", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(requireContext(), "select the options", Toast.LENGTH_SHORT).show()
            }
        }
        return v
    }

   private fun notification() {

       val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
           .setSmallIcon(R.drawable.ic_baseline_notifications_24)
           .setContentTitle("Rms-Query")
           .setContentText("Your Query will be resolve soon..till then have patience ")
           .setPriority(NotificationCompat.PRIORITY_DEFAULT)

       val notificationManager =  requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           val name = "My Notification Channel"
           val descriptionText = "My notification channel description"
           val importance = NotificationManager.IMPORTANCE_DEFAULT
           val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
               description = descriptionText
           }

           notificationManager.createNotificationChannel(channel)
       }

           notificationManager.notify(0, builder.build())

   }
}