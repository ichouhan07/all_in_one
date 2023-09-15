package com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.applligent.hilt_di_retrofit_viewmodel.R
import com.applligent.hilt_di_retrofit_viewmodel.ourApiGet.Model.DataEditArray

class GetAdapter(private val context: Context, private var postList: ArrayList<DataEditArray>) : RecyclerView.Adapter<GetAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return  PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.each_row,parent,false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post=postList[position]
        holder.body.text=post.name
    }

    override fun getItemCount(): Int = postList.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val body: TextView =itemView.findViewById(R.id.body)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(postList: ArrayList<DataEditArray>)
    {
        this.postList=postList
        notifyDataSetChanged()
    }
}