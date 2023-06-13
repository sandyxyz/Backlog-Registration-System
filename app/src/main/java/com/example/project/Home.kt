package com.example.project

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.project.databinding.FragmentHomeBinding
import com.example.project.fragment.Subjects
import com.example.project.fragment.rms
import java.io.ByteArrayOutputStream


class home : Fragment() {
   lateinit var binding : FragmentHomeBinding
   lateinit var register: CardView
   lateinit var feedback: CardView
   lateinit var backlog: CardView
   lateinit var rms: CardView
   lateinit var ai: ImageView
   lateinit var eai: ImageView
    private val pic_id = 123
    lateinit var filepath: Uri


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       register = view.findViewById(R.id.Registration)
       backlog = view.findViewById(R.id.baclog)
       ai =  view.findViewById(R.id.accountImage)
        feedback = view.findViewById(R.id.feedback)
        rms = view.findViewById(R.id.rms)
        eai = view.findViewById(R.id.profilepicedit)
       loaddata()

        val aname  = binding.name3

            val i = activity?.intent?.getStringExtra("name")
            aname.text = i

        loadimage(ai , requireContext())


        rms.setOnClickListener{

            val fm = this.activity?.supportFragmentManager
            val ft = fm?.beginTransaction()
            ft?.replace(R.id.mainframe , rms())
            ft?.commit()
            changename("RMS-Query")
        }

        register.setOnClickListener{

                val fm = this.activity?.supportFragmentManager
                val ft = fm?.beginTransaction()
                ft?.replace(R.id.mainframe , Register())
                ft?.commit()
            changename("Backlog Register")
        }
        backlog.setOnClickListener{

            val fm = this.activity?.supportFragmentManager
            val ft = fm?.beginTransaction()
            ft?.replace(R.id.mainframe , Subjects())
            ft?.commit()
            changename("Subjects")

        }


        feedback.setOnClickListener{
            emojichange()
        }




            eai.setOnClickListener {
                askpermision()
                dialog()
                saveimage(ai, requireContext())
                savedata()
            }



    }

    val ip = registerForActivityResult(ActivityResultContracts.GetContent())
    {
        filepath = it!!
        binding.accountImage.setImageURI(it)
    }
    fun changename(text : String) {
        activity?.title = text
    }

    fun dialog() {

        val d = AlertDialog.Builder(requireContext())
        d.setTitle("Update Image")
        d.setMessage("Pick image from")
        d.setCancelable(true)
        d.setPositiveButton("Camera") { it, which ->
            val camera_intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            } else {
                TODO("VERSION.SDK_INT < CUPCAKE")
            }
            startActivityForResult(camera_intent, pic_id)


        }
        d.setNegativeButton("Storage") { it, which ->
            ip.launch("image/*")

        }
        d.create().show()

    }
    fun askpermision() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0
            )

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireContext(), "Permission not Granted", Toast.LENGTH_SHORT).show()
            }

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {
            // BitMap is data structure of image file which store the image in memory
            val photo = data!!.extras!!["data"] as Bitmap?
            // Set the image in imageview for display
            binding.accountImage.setImageBitmap(photo)


        }
    }
    private fun saveimage(imageview :  ImageView , context : Context)  {
        val shared = this.activity?.getSharedPreferences("bollo" , Context.MODE_PRIVATE)
        val baos = ByteArrayOutputStream()
        val bitmap = imageview.drawable.toBitmap()
        bitmap.compress(Bitmap.CompressFormat.PNG , 100 , baos)
        val encode = Base64.encodeToString(baos.toByteArray() , Base64.DEFAULT)
        with(shared?.edit()){
            this?.putString("hee" , encode)
            this?.apply()
        }
    }
    private fun savedata() {
        val n = binding.name3.text.toString()
        if (n.isEmpty()) {
            Toast.makeText(requireContext(), "please fill data", Toast.LENGTH_SHORT).show()
        } else {

            val sharedp: SharedPreferences? = this.activity?.getSharedPreferences("hello", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor? = sharedp?.edit()

            editor?.apply {
                putString("key1", n)
            }?.apply()

        }

    }
    private fun loadimage(imageview: ImageView , context: Context) {
        val shared = this.activity?.getSharedPreferences("bollo" , Context.MODE_PRIVATE)
        val encode = shared?.getString("hee" , "DEFAULT")
        if(encode != "DEFAULT") {
            val imagebytes = Base64.decode(encode , Base64.DEFAULT)
            val decodeImage = BitmapFactory.decodeByteArray(imagebytes , 0,imagebytes.size)
            imageview.setImageBitmap(decodeImage)
        }
    }
    private fun loaddata() {
        val sharedp: SharedPreferences? = this.activity?.getSharedPreferences("hello", Context.MODE_PRIVATE)
        val name: String? = sharedp?.getString("key1", "")
        binding.name3.text = name

    }

    private fun emojichange() {
        var d = Dialog(requireContext())
        var inflater = LayoutInflater.from(requireContext())
        val v = inflater.inflate(R.layout.rate_us_layout, null)
        var lbtn: Button = v.findViewById(R.id.laterbtn)
        var rbtn: Button = v.findViewById(R.id.ratenow)
        var rating: RatingBar = v.findViewById(R.id.rating)
        var emoji: ImageView = v.findViewById(R.id.emoji)

        rating.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { _, _, _ -> // Do something when the rating changes
                val rate = rating.rating
                if (rate <= 1) {
                    emoji.setImageResource(R.drawable.z1)
                } else if (rate <= 2) {
                    emoji.setImageResource(R.drawable.z2)
                } else if (rate <= 3) {
                    emoji.setImageResource(R.drawable.z3)
                } else if (rate <= 4) {
                    emoji.setImageResource(R.drawable.z4)
                } else {
                    emoji.setImageResource(R.drawable.z5)
                }
                lbtn.setOnClickListener {
                    Toast.makeText(
                        requireContext(),
                        "Beta feedback dedena baad me",
                        Toast.LENGTH_SHORT
                    ).show()
                    d.dismiss()
                }

                rbtn.setOnClickListener {
                    Toast.makeText(
                        requireContext(),
                        "Thanks for your $rate rating",
                        Toast.LENGTH_SHORT
                    ).show()
                    d.dismiss()
                }
            }

        d.setContentView(v)
        d.setCancelable(true)
        d.create()
        d.show()


    }



}

