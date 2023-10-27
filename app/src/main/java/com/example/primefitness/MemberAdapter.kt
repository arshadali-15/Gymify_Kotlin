package com.example.primefitness

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.primefitness.R

class MemberAdapter(private val listMembers : List<MemberModel>) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.item_member, parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listMembers[position]
        holder.name.text = item.name
//        holder.img.setImageResource(item.img)
     //   holder.duration.text = item.duration

    }

    override fun getItemCount(): Int {
        return  listMembers.size
    }

    class ViewHolder(val item : View): RecyclerView.ViewHolder(item) {

        val name= item.findViewById<TextView>(R.id.name)
//        val img = item.findViewById<ImageView>(R.id.profile_pic)
//        val duration = item.findViewById<EditText>(R.id.edit_duration)

    }
}