package com.example.a2cars.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.a2cars.MainActivity
import com.example.a2cars.R
import com.example.a2cars.databinding.ActivityProfileBinding
import com.example.a2cars.model.usermodel
import com.google.android.gms.cast.framework.media.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProfileBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var storage:FirebaseStorage
    private lateinit var database:FirebaseDatabase
    private lateinit var selectedImg:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.BLUE))
        storage=FirebaseStorage.getInstance()
        database=FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()

        binding.floatingActionButton2.setOnClickListener {
//            ImagePicker.with(this)
//                .crop()	    			//Crop image(Optional), Check Customization for more option
//                .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//                .start()
           // galleryLauncher.launch("imageViewPerson")
//            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            changeImage.launch(pickImg)

            val intent=Intent()
            intent.action=Intent.ACTION_GET_CONTENT
            intent.type="image/*"
            startActivityForResult(intent,1)
        }


        binding.btnProfile.setOnClickListener {
           if(binding.userName.text!!.isEmpty()){
               Toast.makeText(this,"please enter your name",Toast.LENGTH_SHORT).show()
           }
            else if(selectedImg==null){
                Toast.makeText(this,"Please select img",Toast.LENGTH_SHORT).show()
           }
            else{
                 uploadData()
           }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data !=null){
            if(data.data!=null){
                selectedImg=data.data!!
                binding.imageViewPerson.setImageURI(selectedImg)
            }
        }
    }

    private fun uploadData() {
        val reference=storage.reference.child("profile").child(Date().time.toString())
         reference.putFile(selectedImg).addOnCompleteListener {
             if (it.isSuccessful){
                 reference.downloadUrl.addOnSuccessListener {task->
                       uploadInfo(task.toString())
                 }
             }
         }
    }

    private fun uploadInfo(imgUrl: String) {
       val user=usermodel(auth.uid.toString(),binding.userName.text.toString(),auth.currentUser!!.phoneNumber.toString(),imgUrl)

        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"Data inserted",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
    }

//    private val changeImage =
//        registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) {
//            if (it.resultCode == Activity.RESULT_OK) {
//                val data = it.data
//               // val imgUri = data?.data
//                imageUri= data.data
//
//                binding.imageViewPerson.setImageURI(imageUri)
//            }
//        }
}