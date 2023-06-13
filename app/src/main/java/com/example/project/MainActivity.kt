package com.example.project

import android.content.Intent
import android.os.Build.VERSION_CODES.S
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.example.project.databinding.ActivityMainBinding
import com.example.project.fragment.Subjects
import com.example.project.fragment.TL
import com.example.project.fragment.profile
import com.example.project.fragment.rms
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
     lateinit var text : TextView
    lateinit var  toggle : ActionBarDrawerToggle
     lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changefragment(home())



//        text = findViewById(R.id.name3)
////        val content =  intent.getStringExtra("name")
////        text.text = content

        binding.bottombar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home-> {
                    changefragment(home())
                    changename("Home")
                }
                R.id.registration-> {
                    changefragment(Register())
                    changename("Backlog Registration")
                }
                R.id.backlog-> {
                    changefragment(Subjects())
                    changename("Subjects")
                }
                R.id.subjects ->{
                    changefragment(rms())
                    changename("RMS-Query")
                }


                else -> {
                }
            }
            true
        }

        toggle = ActionBarDrawerToggle(this,binding.root,R.string.app_name,R.string.app_name)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navbar.setNavigationItemSelectedListener{
            when(it.itemId)   {
                R.id.tl -> {
                    changefragment(TL())
                    changename("Teacher on Leave")
                }


                R.id.profile -> {
                    changefragment(profile())
                    changename("Profile")
                }
                R.id.share -> Toast.makeText(this, "This feature will be soon available", Toast.LENGTH_SHORT).show()

            }
            true
        }

        val navView: NavigationView = findViewById(R.id.navbar)
        val logout : TextView = navView.findViewById(R.id.Logout)

        logout.setOnClickListener {
            // Handle logout button click here
            startActivity(Intent(this , Login2::class.java))
            // For example, you can clear user session, navigate to login screen, etc.
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun changename(text : String) {
        title = text
    }

    fun changefragment(fragment : Fragment){
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.mainframe , fragment)
        ft.commit()
    }
}

