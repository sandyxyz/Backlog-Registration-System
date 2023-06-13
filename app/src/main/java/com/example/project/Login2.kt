package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.project.databinding.ActivityLogin2Binding
import com.google.firebase.auth.FirebaseAuth

class Login2 : AppCompatActivity() {
    lateinit var binding : ActivityLogin2Binding
    lateinit var firebase : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firebase = FirebaseAuth.getInstance()
        binding.loginbtn.setOnClickListener {
            login()
        }
        binding.asign.setOnClickListener{
            startActivity(Intent(this , SignUp::class.java))
        }

    }

    private fun login() {
        var email = binding.username.text.toString()
        var pass = binding.password.text.toString()
        var name =  binding.name.text.toString()

        if(email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty()){
            firebase.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if(it.isSuccessful){
                    var i =  Intent(this , MainActivity::class.java)
                    i.putExtra("name" , name)
                    startActivity(i)
                }else {
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            Toast.makeText(this, "please fill the data", Toast.LENGTH_SHORT).show()
        }
    }
}