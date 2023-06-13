package com.example.project.adapter

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.icu.text.Transliterator.Position
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.project.R
import com.example.project.`class`.alarmreceiver
import com.example.project.data.yserdatra
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class baclklogadapter(var c : Context , var list :  ArrayList<yserdatra>)  : Adapter<baclklogadapter.viewholder>() {
    @RequiresApi(Build.VERSION_CODES.M)
    inner class viewholder(v: View) : ViewHolder(v) {
        var name: TextView
        var date: TextView
        var image: ImageView
        var btn: Button
        val CHANNEL_ID = "my_channel_id"


        init {
            name = v.findViewById(R.id.sname)
            date = v.findViewById(R.id.date)
            image = v.findViewById(R.id.updateanddelete)
            btn = v.findViewById(R.id.setreminder)

            image.setOnClickListener {
                popupmenu(it)
            }
            btn.setOnClickListener {

                notification()
                Toast.makeText(c, "Reminder is set", Toast.LENGTH_SHORT).show()
            }
        }


        private fun popupmenu(v: View) {
            val pm = PopupMenu(c, v)
            pm.inflate(R.menu.backlogmenu)
            pm.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> {
                        AlertDialog.Builder(c).setTitle("Delete")
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setMessage("Are you sure you want to delete this information ")
                            .setPositiveButton("Yes") {

                                    dialog, _ ->
                                list.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c, "Deleted", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()

                            }
                            .create()
                            .show()
                        true
                    }
                    else -> true
                }
            }

            pm.show()
        }

        private fun notification() {

            val builder = NotificationCompat.Builder(c, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle("Alarm ")
                .setContentText("Alarm will be ring at 30 minutes before the time of exam ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager =
                c.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): baclklogadapter.viewholder {
            val i = LayoutInflater.from(parent.context)
            val r = i.inflate(R.layout.backlog_subject_holder_layout, parent, false)
            return viewholder(r)
        }


        @RequiresApi(Build.VERSION_CODES.M)
        override fun onBindViewHolder(holder: baclklogadapter.viewholder, position: Int) {
            var n = list[position]
            holder.name.text = n.subject
            holder.date.text = n.date
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }