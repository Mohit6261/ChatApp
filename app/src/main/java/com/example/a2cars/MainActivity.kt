package com.example.a2cars

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.a2cars.activity.NumberActivity
import com.example.a2cars.adapter.ViewPagerAdapter
import com.example.a2cars.databinding.ActivityMainBinding
import com.example.a2cars.ui.CallFragment
import com.example.a2cars.ui.ChatFragment
import com.example.a2cars.ui.StatusFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()

        if (auth.currentUser==null){
            val intent= Intent(this,NumberActivity::class.java)
            startActivity(intent)
            finish()
        }
        val fragmentArrayList=ArrayList<Fragment>()

        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(CallFragment())

        val adapter=ViewPagerAdapter(this,supportFragmentManager,fragmentArrayList)
        binding.viewpager.adapter=adapter
        binding.tabs.setupWithViewPager(binding.viewpager)

    }
}