package com.example.primefitness

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import java.util.Collections
import java.util.Random


class HomeFragment : Fragment() {

    lateinit var adapter: MemberAdapter
    lateinit var recycler: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var noDataText :TextView


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

    override fun onStart() {
        super.onStart()

        adapter.notifyDataSetChanged()

        swipeRefreshLayout.setOnRefreshListener {

            // on below line we are setting is refreshing to false.
            swipeRefreshLayout.isRefreshing = false

            adapter.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noDataText = requireView().findViewById(R.id.noMember_text)
        recycler = requireView().findViewById<RecyclerView>(R.id.recycler_view)
        swipeRefreshLayout=requireView().findViewById(R.id.swipe_refresh_layout)

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

                    if(listMembers.isEmpty()){
                        noDataText.visibility = View.VISIBLE
                    }

                    adapter = MemberAdapter(listMembers)

                    recycler.layoutManager = LinearLayoutManager(requireContext())

                    recycler.adapter = adapter

                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            }

        adapter.notifyDataSetChanged()

        swipeRefreshLayout.setOnRefreshListener {

            // on below line we are setting is refreshing to false.
            swipeRefreshLayout.isRefreshing = false

            adapter.notifyDataSetChanged()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()

    }
}

