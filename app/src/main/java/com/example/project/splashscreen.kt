package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class splashscreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)


        var image : ImageView = findViewById(R.id.simage)
        image.alpha = 0f
        image.animate().setDuration(1500).alpha(1f).withEndAction{
            val intent = Intent(this , SignUp::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out)
            finish()
        }
    }


}