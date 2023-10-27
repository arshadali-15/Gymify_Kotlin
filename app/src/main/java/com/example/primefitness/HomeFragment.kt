package com.example.primefitness

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore


class HomeFragment : Fragment() {

    lateinit var adapter: MemberAdapter
    lateinit var recycler: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recycler = requireView().findViewById<RecyclerView>(R.id.recycler_view)


        val addMember = requireView().findViewById<FloatingActionButton>(R.id.add_fab)

        addMember.setOnClickListener {
            val intent = Intent(requireContext(), Form::class.java)
            startActivity(intent)
        }

        EventChangeListener()


    }


    private fun EventChangeListener() {
        val firestore = Firebase.firestore

        firestore.collection("users").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val listMembers: ArrayList<MemberModel> = ArrayList()
                    for (data in it.result) {
                        val gymMember: MemberModel? = data.toObject(MemberModel::class.java)

                        if (gymMember != null) {
                            listMembers.add(gymMember)
                        }
                    }

                    adapter = MemberAdapter(listMembers)

                    recycler.layoutManager = LinearLayoutManager(requireContext())

                    recycler.adapter = adapter
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()

    }
}

