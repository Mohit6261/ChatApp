package com.example.a2cars.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2cars.R
import com.example.a2cars.adapter.ChatAdapter
import com.example.a2cars.databinding.FragmentChatBinding

import com.example.a2cars.model.usermodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatFragment : Fragment() {

  private lateinit var binding:FragmentChatBinding
   private  var database:FirebaseDatabase?=null
   private lateinit var userList:ArrayList<usermodel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding=FragmentChatBinding.inflate(layoutInflater,container,false)

        database=FirebaseDatabase.getInstance()
         userList= ArrayList()

        database!!.reference.child("users")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    userList.clear()

                    for (snapshot1 in snapshot.children){
                        val user=snapshot1.getValue(usermodel::class.java)
                        if (user!!.uid != FirebaseAuth.getInstance().uid ){
                            userList.add(user)
                        }
                    }

                   binding.recyclerView.adapter=ChatAdapter(requireContext(),userList)

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Database read failed.", error.toException())
                }

           })


        return binding.root
    }

}