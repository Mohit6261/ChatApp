package com.example.a2cars.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.a2cars.R
import com.example.a2cars.databinding.ActivityOtpactivityBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var auth:FirebaseAuth
    private  lateinit var verificationId:String
    private lateinit var dialog:AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()

        val builder=AlertDialog.Builder(this)
        builder.setMessage("please wait...")
        builder.setTitle("loading")
        builder.setCancelable(false)

        dialog=builder.create()
        dialog.show()
        val phoneNumber="+91"+intent.getStringExtra("number")

        val options= PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@OTPActivity,"please try again${p0} ",Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    Toast.makeText(this@OTPActivity,"okkk ",Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    verificationId=p0
                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.button2.setOnClickListener {
            if (binding.otp.text!!.isEmpty()){
                Toast.makeText(this,"enter otp",Toast.LENGTH_SHORT).show()
            }
            else{
                dialog.show()
                val credential=PhoneAuthProvider.getCredential(verificationId,binding.otp.text!!.toString())

                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful){
                        dialog.dismiss()
                        startActivity(Intent(this,ProfileActivity::class.java))
                        finish()
                    }
                    else{
                        dialog.dismiss()
                        Toast.makeText(this,"error ${it.exception}",Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }
    }
}