package com.example.primefitness

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.famsafe.MemberAdapter
import com.example.famsafe.MemberModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {


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

        val addMember = requireView().findViewById<FloatingActionButton>(R.id.add_fab)

        addMember.setOnClickListener{
            val intent = Intent(requireContext() ,Form::class.java )
            startActivity(intent)
        }

        val recycler = requireView().findViewById<RecyclerView>(R.id.recycler_view)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val listMembers = listOf<MemberModel>(
            MemberModel("Arshadali", R.drawable.man1),
            MemberModel("Hrushal", R.drawable.man2),
            MemberModel("Sanket", R.drawable.man3),
            MemberModel("Akhilesh", R.drawable.man1),
            MemberModel("Abidali", R.drawable.man1),

            )

        val adapter = MemberAdapter(listMembers)

        recycler.adapter=adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()

    }
}