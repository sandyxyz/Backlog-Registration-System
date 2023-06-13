package com.example.project.`class`

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import com.example.project.R


class alarmreceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "Alarm is going off", Toast.LENGTH_SHORT).show()

        // Play the alarm sound
        val mediaPlayer = MediaPlayer.create(p0 ,  R.raw.mymusic)
        mediaPlayer.start()

        // Stop the alarm after 30 seconds
        Thread {
            Thread.sleep(30_000)
            mediaPlayer.stop()
            mediaPlayer.release()
        }.start()
    }

}