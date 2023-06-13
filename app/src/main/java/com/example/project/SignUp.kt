package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.project.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    lateinit var  firebase : FirebaseAuth
    lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val t : TextView = findViewById(R.id.alredy)

        firebase  =  FirebaseAuth.getInstance()
        t.setOnClickListener{
            val i = Intent(this , Login2::class.java)
            startActivity(i)
        }
        binding.createbtn.setOnClickListener{
            createacc()
        }

    }
    private fun createacc(){
        var name = binding.username.text.toString()
        var email = binding.email.text.toString()
        var pass  = binding.password.text.toString()
        var cpass = binding.cpassword.text.toString()

        if(name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && cpass.isNotEmpty()){
            if(pass == cpass){
                firebase.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this){
                    if (it.isSuccessful){
                        val intent = Intent(this,Login2::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else Toast.makeText(this, "Password and Confirm password does not match", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this, "Please fill all the details ", Toast.LENGTH_SHORT).show()


    }
    override fun onStart() {
        super.onStart()
        if(firebase.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Already Login", Toast.LENGTH_SHORT).show()
        }
    }
}
